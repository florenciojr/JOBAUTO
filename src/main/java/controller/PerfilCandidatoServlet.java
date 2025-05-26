package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import dao.PerfilCandidatos;
import model.PerfilCandidato;

@WebServlet(name = "PerfilCandidatoServlet", urlPatterns = {"/perfil-candidato"})
public class PerfilCandidatoServlet extends HttpServlet {
    private static final String PAGINA_LISTAR = "/WEB-INF/views/perfil-candidato/listar.jsp";
    private static final String PAGINA_FORMULARIO = "/WEB-INF/views/perfil-candidato/formulario.jsp";
    private static final String PAGINA_DETALHES = "/WEB-INF/views/perfil-candidato/detalhes.jsp";
    
    private final PerfilCandidatos perfilDAO = new PerfilCandidatos();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro action é obrigatório");
                return;
            }
            
            switch(action) {
                case "salvar":
                    salvarPerfil(request, response);
                    break;
                case "atualizar":
                    atualizarPerfil(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida: " + action);
            }
        } catch (Exception e) {
            tratarErro(request, response, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.isEmpty()) {
                listarPerfis(request, response);
                return;
            }
            
            switch(action) {
                case "listar":
                    listarPerfis(request, response);
                    break;
                case "detalhes":
                    detalharPerfil(request, response);
                    break;
                case "editar":
                    editarPerfil(request, response);
                    break;
                case "excluir":
                    excluirPerfil(request, response);
                    break;
                case "novo":
                    novoPerfil(request, response);
                    break;
                case "buscar":
                    buscarPerfis(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada: " + action);
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagemErro", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/perfil-candidato?action=listar");
        }
    }

    private void salvarPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        PerfilCandidato perfil = criarPerfilFromRequest(request);
        
        if (perfilDAO.inserirPerfil(perfil)) {
            enviarMensagemSucesso(request, "Perfil criado com sucesso!");
            response.sendRedirect(request.getContextPath() + "/perfil-candidato?action=detalhes&usuarioId=" + perfil.getUsuarioId());
        } else {
            throw new ServletException("Falha ao criar perfil no banco de dados");
        }
    }

    private void atualizarPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        PerfilCandidato perfil = buscarPerfilOuFalhar(usuarioId);
        
        atualizarPerfilFromRequest(request, perfil);
        
        if (perfilDAO.atualizarPerfil(perfil)) {
            enviarMensagemSucesso(request, "Perfil atualizado com sucesso!");
            response.sendRedirect(request.getContextPath() + "/perfil-candidato?action=detalhes&usuarioId=" + usuarioId);
        } else {
            throw new ServletException("Falha ao atualizar perfil");
        }
    }

    private void listarPerfis(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<PerfilCandidato> perfis = perfilDAO.listarTodos();
        request.setAttribute("perfis", perfis);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_LISTAR).forward(request, response);
    }

    private void detalharPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        PerfilCandidato perfil = buscarPerfilOuFalhar(usuarioId);
        
        request.setAttribute("perfil", perfil);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_DETALHES).forward(request, response);
    }

    private void editarPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        PerfilCandidato perfil = buscarPerfilOuFalhar(usuarioId);
        
        request.setAttribute("perfil", perfil);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
    }

    private void novoPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        PerfilCandidato perfil = new PerfilCandidato();
        // Garante que o usuarioId seja null (não zero)
        perfil.setUsuarioId(0); // Ou você pode remover esta linha completamente
        
        request.setAttribute("perfil", perfil);
        transferirMensagensSessaoParaRequest(request);
        request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
    }

    private void excluirPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        
        if (perfilDAO.excluirPerfil(usuarioId)) {
            enviarMensagemSucesso(request, "Perfil excluído com sucesso!");
        } else {
            enviarMensagemErro(request, "Falha ao excluir perfil");
        }
        
        response.sendRedirect(request.getContextPath() + "/perfil-candidato?action=listar");
    }

    private void buscarPerfis(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String profissao = request.getParameter("profissao");
        List<PerfilCandidato> perfis = perfilDAO.buscarPorProfissao(profissao);
        
        request.setAttribute("perfis", perfis);
        request.setAttribute("termoBusca", profissao);
        request.getRequestDispatcher(PAGINA_LISTAR).forward(request, response);
    }

    private PerfilCandidato criarPerfilFromRequest(HttpServletRequest request) throws ServletException {
        PerfilCandidato perfil = new PerfilCandidato();
        
        try {
            perfil.setUsuarioId(Integer.parseInt(request.getParameter("usuarioId")));
        } catch (NumberFormatException e) {
            throw new ServletException("ID do usuário inválido");
        }
        
        perfil.setProfissao(request.getParameter("profissao"));
        if (perfil.getProfissao() == null || perfil.getProfissao().trim().isEmpty()) {
            throw new ServletException("Profissão é obrigatória");
        }
        
        perfil.setResumo(request.getParameter("resumo"));
        if (perfil.getResumo() == null || perfil.getResumo().trim().isEmpty()) {
            throw new ServletException("Resumo é obrigatório");
        }
        
        perfil.setHabilidades(request.getParameter("habilidades"));
        perfil.setExperiencia(request.getParameter("experiencia"));
        perfil.setPretensaoSalarial(request.getParameter("pretensaoSalarial"));
        
        return perfil;
    }

    private void atualizarPerfilFromRequest(HttpServletRequest request, PerfilCandidato perfil) {
        perfil.setProfissao(request.getParameter("profissao"));
        perfil.setResumo(request.getParameter("resumo"));
        perfil.setHabilidades(request.getParameter("habilidades"));
        perfil.setExperiencia(request.getParameter("experiencia"));
        perfil.setPretensaoSalarial(request.getParameter("pretensaoSalarial"));
    }

    private PerfilCandidato buscarPerfilOuFalhar(int usuarioId) throws ServletException {
        PerfilCandidato perfil = perfilDAO.buscarPorUsuarioId(usuarioId);
        if (perfil == null) {
            throw new ServletException("Perfil não encontrado para o usuário ID: " + usuarioId);
        }
        return perfil;
    }

    private void transferirMensagensSessaoParaRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("mensagemSucesso") != null) {
                request.setAttribute("mensagemSucesso", session.getAttribute("mensagemSucesso"));
                session.removeAttribute("mensagemSucesso");
            }
            if (session.getAttribute("mensagemErro") != null) {
                request.setAttribute("mensagemErro", session.getAttribute("mensagemErro"));
                session.removeAttribute("mensagemErro");
            }
        }
    }

    private void enviarMensagemSucesso(HttpServletRequest request, String mensagem) {
        request.getSession().setAttribute("mensagemSucesso", mensagem);
    }

    private void enviarMensagemErro(HttpServletRequest request, String mensagem) {
        request.getSession().setAttribute("mensagemErro", mensagem);
    }

    private void tratarErro(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        request.setAttribute("erro", e.getMessage());
        
        if (request.getMethod().equalsIgnoreCase("POST")) {
            PerfilCandidato perfil = new PerfilCandidato();
            try {
                perfil.setUsuarioId(Integer.parseInt(request.getParameter("usuarioId")));
            } catch (NumberFormatException ex) {
                // Ignora se não conseguir converter o ID
            }
            perfil.setProfissao(request.getParameter("profissao"));
            perfil.setResumo(request.getParameter("resumo"));
            perfil.setHabilidades(request.getParameter("habilidades"));
            perfil.setExperiencia(request.getParameter("experiencia"));
            perfil.setPretensaoSalarial(request.getParameter("pretensaoSalarial"));
            
            request.setAttribute("perfil", perfil);
            request.getRequestDispatcher(PAGINA_FORMULARIO).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/perfil-candidato?action=listar");
        }
    }
}