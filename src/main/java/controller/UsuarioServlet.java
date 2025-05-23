package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import dao.Usuarios;
import model.Usuario;
import util.Seguranca;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
    private Usuarios usuarioDAO = new Usuarios();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if("cadastrar".equals(action)) {
            cadastrarUsuario(request, response);
        } else if("atualizar".equals(action)) {
            atualizarUsuario(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if("listar".equals(action)) {
            listarUsuarios(request, response);
        } else if("editar".equals(action)) {
            editarUsuario(request, response);
        } else if("excluir".equals(action)) {
            excluirUsuario(request, response);
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setSenha(Seguranca.criptografar(request.getParameter("senha")));
        usuario.setTipo(request.getParameter("tipo"));
        usuario.setTelefone(request.getParameter("telefone"));
        usuario.setAtivo(true);
        usuario.setDataCadastro(java.time.LocalDate.now().toString());
        
        if(usuarioDAO.inserirUsuario(usuario)) {
            response.sendRedirect("usuario?action=listar&sucesso=Usuário+cadastrado+com+sucesso");
        } else {
            response.sendRedirect("cadastro-usuario.jsp?erro=Erro+ao+cadastrar+usuário");
        }
    }

    private void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = usuarioDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
        
        if(usuario != null) {
            usuario.setNome(request.getParameter("nome"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setTelefone(request.getParameter("telefone"));
            usuario.setTipo(request.getParameter("tipo"));
            usuario.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));
            
            if(usuarioDAO.atualizarUsuario(usuario)) {
                response.sendRedirect("usuario?action=listar&sucesso=Usuário+atualizado+com+sucesso");
            } else {
                response.sendRedirect("editar-usuario.jsp?id=" + usuario.getId() + "&erro=Erro+ao+atualizar+usuário");
            }
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/views/listar-usuarios.jsp").forward(request, response);
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = usuarioDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/WEB-INF/views/editar-usuario.jsp").forward(request, response);
    }

    private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        if(usuarioDAO.excluirUsuario(id)) {
            response.sendRedirect("usuario?action=listar&sucesso=Usuário+excluído+com+sucesso");
        } else {
            response.sendRedirect("usuario?action=listar&erro=Erro+ao+excluir+usuário");
        }
    }
}