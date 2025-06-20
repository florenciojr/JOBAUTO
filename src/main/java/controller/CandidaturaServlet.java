package controller;

import dao.Candidaturas;
import model.Candidatura;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/candidaturas")
public class CandidaturaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Candidaturas candidaturasDao;

    @Override
    public void init() throws ServletException {
        super.init();
        candidaturasDao = new Candidaturas();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteCandidatura(request, response);
                    break;
                case "byVaga":
                    listByVaga(request, response);
                    break;
                case "byCandidato":
                    listByCandidato(request, response);
                    break;
                case "list":
                default:
                    listCandidaturas(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "insert":
                    insertCandidatura(request, response);
                    break;
                case "update":
                    updateCandidatura(request, response);
                    break;
                case "updateStatus":
                    updateStatus(request, response);
                    break;
                default:
                    listCandidaturas(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void listCandidaturas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("candidaturas", candidaturasDao.listarTodas());
        request.getRequestDispatcher("/WEB-INF/views/candidatura/list.jsp").forward(request, response);
    }

    private void listByVaga(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int vagaId = Integer.parseInt(request.getParameter("vagaId"));
        request.setAttribute("candidaturas", candidaturasDao.buscarPorVaga(vagaId));
        request.setAttribute("vagaId", vagaId);
        request.getRequestDispatcher("/WEB-INF/views/candidatura/listByVaga.jsp").forward(request, response);
    }

    private void listByCandidato(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int candidatoId = Integer.parseInt(request.getParameter("candidatoId"));
        request.setAttribute("candidaturas", candidaturasDao.buscarPorCandidato(candidatoId));
        request.setAttribute("candidatoId", candidatoId);
        request.getRequestDispatcher("/WEB-INF/views/candidatura/listByCandidato.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/candidatura/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Candidatura candidatura = candidaturasDao.buscarPorId(id);
        request.setAttribute("candidatura", candidatura);
        request.getRequestDispatcher("/WEB-INF/views/candidatura/form.jsp").forward(request, response);
    }

    private void insertCandidatura(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            Candidatura candidatura = extractCandidaturaFromRequest(request);
            candidatura.setDataCandidatura(LocalDateTime.now().toString());
            
            if (candidaturasDao.inserir(candidatura)) {
                session.setAttribute("successMessage", "Candidatura criada com sucesso!");
            } else {
                session.setAttribute("errorMessage", "Falha ao criar candidatura.");
            }
            response.sendRedirect("candidaturas");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Erro: " + e.getMessage());
            request.setAttribute("candidatura", extractCandidaturaFromRequest(request));
            request.getRequestDispatcher("/WEB-INF/views/candidatura/form.jsp").forward(request, response);
        }
    }

    private void updateCandidatura(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        try {
            Candidatura candidatura = extractCandidaturaFromRequest(request);
            candidatura.setId(Integer.parseInt(request.getParameter("id")));
            
            if (candidaturasDao.atualizar(candidatura)) {
                session.setAttribute("successMessage", "Candidatura atualizada com sucesso!");
            } else {
                session.setAttribute("errorMessage", "Falha ao atualizar candidatura.");
            }
            response.sendRedirect("candidaturas");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Erro: " + e.getMessage());
            request.setAttribute("candidatura", extractCandidaturaFromRequest(request));
            request.getRequestDispatcher("/WEB-INF/views/candidatura/form.jsp").forward(request, response);
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");
            
            Candidatura candidatura = candidaturasDao.buscarPorId(id);
            candidatura.setStatus(status);
            
            if (candidaturasDao.atualizar(candidatura)) {
                session.setAttribute("successMessage", "Status atualizado com sucesso!");
            } else {
                session.setAttribute("errorMessage", "Falha ao atualizar status.");
            }
            
            String referer = request.getHeader("referer");
            response.sendRedirect(referer != null ? referer : "candidaturas");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Erro: " + e.getMessage());
            response.sendRedirect("candidaturas");
        }
    }

    private void deleteCandidatura(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            if (candidaturasDao.deletar(id)) {
                session.setAttribute("successMessage", "Candidatura excluída com sucesso!");
            } else {
                session.setAttribute("errorMessage", "Falha ao excluir candidatura.");
            }
            response.sendRedirect("candidaturas");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Erro: " + e.getMessage());
            response.sendRedirect("candidaturas");
        }
    }

    private Candidatura extractCandidaturaFromRequest(HttpServletRequest request) {
        Candidatura candidatura = new Candidatura();
        candidatura.setVagaId(Integer.parseInt(request.getParameter("vagaId")));
        candidatura.setCandidatoId(Integer.parseInt(request.getParameter("candidatoId")));
        candidatura.setTipo(request.getParameter("tipo"));
        candidatura.setStatus(request.getParameter("status"));
        return candidatura;
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Ocorreu um erro: " + e.getMessage());
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}
