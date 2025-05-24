package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import dao.Usuarios;
import model.Usuario;
import util.Seguranca;
import java.util.logging.*;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class UsuarioServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsuarioServlet.class.getName());
    private static final String PAGINA_LISTAR = "/WEB-INF/views/usuarios/listar.jsp";
    private static final String PAGINA_FORMULARIO = "/WEB-INF/views/usuarios/formulario.jsp";
    private static final String UPLOAD_DIR = "uploads";
    private static final String[] EXTENSOES_PERMITIDAS = {".jpg", ".jpeg", ".png", ".gif"};
    private static final long MAX_TAMANHO_FOTO = 1024 * 1024 * 2; // 2MB
    
    private Usuarios usuarioDAO = new Usuarios();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            switch(action != null ? action : "") {
                case "cadastrar":
                    cadastrarUsuario(request, response);
                    break;
                case "atualizar":
                    atualizarUsuario(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro na ação " + action, e);
            request.setAttribute("erro", "Ocorreu um erro: " + e.getMessage());
            request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if(action == null || action.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/usuario?action=listar");
                return;
            }
            
            switch(action) {
                case "listar":
                    listarUsuarios(request, response);
                    break;
                case "editar":
                    editarUsuario(request, response);
                    break;
                case "excluir":
                    excluirUsuario(request, response);
                    break;
                case "novo":
                    novoUsuario(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro na ação " + action, e);
            request.getSession().setAttribute("mensagemErro", "Erro ao processar requisição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/usuario?action=listar");
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Usuario usuario = criarUsuarioFromRequest(request);
        usuario.setAtivo(true);
        usuario.setDataCadastro(new java.util.Date().toString());
        
        try {
            validarUsuario(usuario);
            processarUploadFoto(request, usuario);
            
            if(usuarioDAO.inserirUsuario(usuario)) {
                request.getSession().setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/usuario?action=listar");
            } else {
                throw new ServletException("Falha ao cadastrar usuário no banco de dados");
            }
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
        }
    }

    private void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.buscarPorId(id);
        
        if(usuario == null) {
            throw new ServletException("Usuário não encontrado com ID: " + id);
        }
        
        try {
            atualizarUsuarioFromRequest(request, usuario);
            validarUsuario(usuario);
            processarUploadFoto(request, usuario);
            
            if(usuarioDAO.atualizarUsuario(usuario)) {
                request.getSession().setAttribute("mensagemSucesso", "Usuário atualizado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/usuario?action=listar");
            } else {
                throw new ServletException("Falha ao atualizar usuário no banco de dados");
            }
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            request.setAttribute("usuarios", usuarios);
            transferirMensagensSessaoParaRequest(request);
            request.getRequestDispatcher(PAGINA_LISTAR).forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao listar usuários", e);
            throw new ServletException("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.buscarPorId(id);
        
        if(usuario == null) {
            throw new ServletException("Usuário não encontrado com ID: " + id);
        }
        
        request.setAttribute("usuario", usuario);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
    }

    private void novoUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Usuario usuario = new Usuario();
        usuario.setId(0);
        usuario.setAtivo(true);
        request.setAttribute("usuario", usuario);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
    }

    private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if(usuario == null) {
                throw new ServletException("Usuário não encontrado com ID: " + id);
            }
            
            if(usuarioDAO.excluirUsuario(id)) {
                removerFotoAntiga(usuario.getFotoPerfil());
                request.getSession().setAttribute("mensagemSucesso", "Usuário excluído com sucesso!");
            } else {
                request.getSession().setAttribute("mensagemErro", "Falha ao excluir usuário");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao excluir usuário", e);
            request.getSession().setAttribute("mensagemErro", "Erro ao excluir usuário: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/usuario?action=listar");
    }

    private Usuario criarUsuarioFromRequest(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(sanitize(request.getParameter("nome")));
        usuario.setEmail(sanitize(request.getParameter("email")));
        usuario.setSenha(Seguranca.criptografar(request.getParameter("senha")));
        usuario.setTipo(sanitize(request.getParameter("tipo")));
        usuario.setTelefone(sanitize(request.getParameter("telefone")));
        return usuario;
    }

    private void atualizarUsuarioFromRequest(HttpServletRequest request, Usuario usuario) {
        usuario.setNome(sanitize(request.getParameter("nome")));
        usuario.setTipo(sanitize(request.getParameter("tipo")));
        usuario.setTelefone(sanitize(request.getParameter("telefone")));
        usuario.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));
        
        String novaSenha = request.getParameter("senha");
        if(novaSenha != null && !novaSenha.isEmpty()) {
            usuario.setSenha(Seguranca.criptografar(novaSenha));
        }
    }

    private String sanitize(String input) {
        if (input == null) return null;
        return input.replace("<", "&lt;").replace(">", "&gt;");
    }

    private void validarUsuario(Usuario usuario) throws ServletException {
        if(usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new ServletException("Nome é obrigatório");
        }
        
        if(usuario.getEmail() == null || !usuario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ServletException("Email inválido");
        }
        
        if(usuario.getId() == 0 && (usuario.getSenha() == null || usuario.getSenha().isEmpty())) {
            throw new ServletException("Senha é obrigatória para novo usuário");
        }
        
        if(usuario.getTipo() == null || (!usuario.getTipo().equals("CANDIDATO") && 
           !usuario.getTipo().equals("EMPRESA") && !usuario.getTipo().equals("ADMIN"))) {
            throw new ServletException("Tipo de usuário inválido");
        }
    }

    private void processarUploadFoto(HttpServletRequest request, Usuario usuario) 
            throws IOException, ServletException {
        
        Part filePart = request.getPart("fotoPerfil");
        if (filePart != null && filePart.getSize() > 0) {
            if (filePart.getSize() > MAX_TAMANHO_FOTO) {
                throw new ServletException("Tamanho do arquivo excede o limite de 2MB");
            }
            
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            
            if (!Arrays.asList(EXTENSOES_PERMITIDAS).contains(fileExtension)) {
                throw new ServletException("Tipo de arquivo não permitido. Use apenas JPG, JPEG, PNG ou GIF");
            }
            
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String newFileName = "user_" + usuario.getId() + "_" + System.currentTimeMillis() + fileExtension;
            String filePath = uploadPath + File.separator + newFileName;
            
            if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isEmpty()) {
                removerFotoAntiga(usuario.getFotoPerfil());
            }
            
            filePart.write(filePath);
            usuario.setFotoPerfil(UPLOAD_DIR + "/" + newFileName);
        }
    }

    private void removerFotoAntiga(String caminhoFoto) {
        if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
            try {
                String fullPath = getServletContext().getRealPath("") + caminhoFoto;
                File oldFile = new File(fullPath);
                if (oldFile.exists()) {
                    if (!oldFile.delete()) {
                        logger.warning("Não foi possível deletar o arquivo: " + fullPath);
                    }
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Erro ao remover foto antiga", e);
            }
        }
    }

    private void transferirMensagensSessaoParaRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            if(session.getAttribute("mensagemSucesso") != null) {
                request.setAttribute("mensagemSucesso", session.getAttribute("mensagemSucesso"));
                session.removeAttribute("mensagemSucesso");
            }
            if(session.getAttribute("mensagemErro") != null) {
                request.setAttribute("mensagemErro", session.getAttribute("mensagemErro"));
                session.removeAttribute("mensagemErro");
            }
        }
    }
}