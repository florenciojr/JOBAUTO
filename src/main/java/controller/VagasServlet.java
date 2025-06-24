package controller;

import dao.Vagas;
import model.Vaga;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

@WebServlet(name = "VagasServlet", urlPatterns = {"/vagas"})
public class VagasServlet extends HttpServlet {
    private final Vagas vagasDao = new Vagas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.isEmpty()) {
                listarVagas(request, response);
                return;
            }
            
            switch (action) {
                case "list":
                    listarVagas(request, response);
                    break;
                case "new":
                    mostrarFormularioNovaVaga(request, response);
                    break;
                case "edit":
                    mostrarFormularioEdicao(request, response);
                    break;
                case "view":
                    mostrarDetalhesVaga(request, response);
                    break;
                case "byEmpresa":
                    listarVagasPorEmpresa(request, response);
                    break;
                case "ativas":
                    listarVagasAtivas(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada");
            }
        } catch (Exception e) {
            tratarErro(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro action é obrigatório");
                return;
            }
            
            switch (action) {
                case "save":
                    salvarVaga(request, response);
                    break;
                case "update":
                    atualizarVaga(request, response);
                    break;
                case "delete":
                    deletarVaga(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida: " + action);
            }
        } catch (Exception e) {
            tratarErro(request, response, e);
        }
    }

    private void listarVagas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vaga> vagas = vagasDao.listarTodas();
        request.setAttribute("vagas", vagas);
        request.getRequestDispatcher("/WEB-INF/views/vagas/listar.jsp").forward(request, response);
    }

    private void listarVagasPorEmpresa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null || usuarioLogado.getTipo() == null || !usuarioLogado.getTipo().equals("EMPRESA")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        List<Vaga> vagas = vagasDao.buscarPorEmpresa(usuarioLogado.getId());
        request.setAttribute("vagas", vagas);
        request.setAttribute("isEmpresaView", true);
        request.getRequestDispatcher("/WEB-INF/views/vagas/listar.jsp").forward(request, response);
    }

    private void listarVagasAtivas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vaga> vagas = vagasDao.buscarAtivas();
        request.setAttribute("vagas", vagas);
        request.setAttribute("isAtivasView", true);
        request.getRequestDispatcher("/WEB-INF/views/vagas/listar.jsp").forward(request, response);
    }

    private void mostrarFormularioNovaVaga(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("acao", "Nova Vaga");
        request.getRequestDispatcher("/WEB-INF/views/vagas/formulario.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Vaga vaga = vagasDao.buscarPorId(id);
        
        if (vaga == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada");
            return;
        }
        
        request.setAttribute("vaga", vaga);
        request.setAttribute("acao", "Editar Vaga");
        request.getRequestDispatcher("/WEB-INF/views/vagas/formulario.jsp").forward(request, response);
    }

    private void mostrarDetalhesVaga(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Vaga vaga = vagasDao.buscarPorId(id);
        
        if (vaga == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada");
            return;
        }
        
        request.setAttribute("vaga", vaga);
        request.getRequestDispatcher("/WEB-INF/views/vagas/detalhes.jsp").forward(request, response);
    }

    private void salvarVaga(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null || !usuarioLogado.getTipo().equals("EMPRESA")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        Vaga vaga = extrairVagaDoRequest(request);
        vaga.setEmpresaId(usuarioLogado.getId());
        vaga.setStatus("ABERTA");
        
        if (vagasDao.inserir(vaga)) {
            session.setAttribute("mensagemSucesso", "Vaga criada com sucesso!");
            response.sendRedirect(request.getContextPath() + "/vagas?action=byEmpresa");
        } else {
            session.setAttribute("mensagemErro", "Falha ao criar vaga");
            request.setAttribute("vaga", vaga);
            request.setAttribute("acao", "Nova Vaga");
            request.getRequestDispatcher("/WEB-INF/views/vagas/formulario.jsp").forward(request, response);
        }
    }

    private void atualizarVaga(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null || !usuarioLogado.getTipo().equals("EMPRESA")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        Vaga vagaExistente = vagasDao.buscarPorId(id);
        
        if (vagaExistente == null || vagaExistente.getEmpresaId() != usuarioLogado.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        Vaga vaga = extrairVagaDoRequest(request);
        vaga.setId(id);
        vaga.setEmpresaId(usuarioLogado.getId());
        vaga.setStatus(vagaExistente.getStatus());
        
        if (vagasDao.atualizar(vaga)) {
            session.setAttribute("mensagemSucesso", "Vaga atualizada com sucesso!");
            response.sendRedirect(request.getContextPath() + "/vagas?action=byEmpresa");
        } else {
            session.setAttribute("mensagemErro", "Falha ao atualizar vaga");
            request.setAttribute("vaga", vaga);
            request.setAttribute("acao", "Editar Vaga");
            request.getRequestDispatcher("/WEB-INF/views/vagas/formulario.jsp").forward(request, response);
        }
    }

    private void deletarVaga(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null || !usuarioLogado.getTipo().equals("EMPRESA")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        Vaga vaga = vagasDao.buscarPorId(id);
        
        if (vaga == null || vaga.getEmpresaId() != usuarioLogado.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        if (vagasDao.deletar(id)) {
            session.setAttribute("mensagemSucesso", "Vaga excluída com sucesso!");
        } else {
            session.setAttribute("mensagemErro", "Falha ao excluir vaga");
        }
        
        response.sendRedirect(request.getContextPath() + "/vagas?action=byEmpresa");
    }

    private Vaga extrairVagaDoRequest(HttpServletRequest request) {
        Vaga vaga = new Vaga();
        vaga.setTitulo(request.getParameter("titulo"));
        vaga.setDescricao(request.getParameter("descricao"));
        vaga.setRequisitos(request.getParameter("requisitos"));
        vaga.setBeneficios(request.getParameter("beneficios"));
        vaga.setSalario(request.getParameter("salario"));
        vaga.setTipoContrato(request.getParameter("tipoContrato"));
        vaga.setModalidade(request.getParameter("modalidade"));
        vaga.setLocalizacao(request.getParameter("localizacao"));
        vaga.setDataPublicacao(request.getParameter("dataPublicacao"));
        vaga.setDataEncerramento(request.getParameter("dataEncerramento"));
        vaga.setValorRecrutamento(Double.parseDouble(request.getParameter("valorRecrutamento")));
        return vaga;
    }

    private void tratarErro(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("mensagemErro", "Ocorreu um erro: " + e.getMessage());
        request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
    }
}
