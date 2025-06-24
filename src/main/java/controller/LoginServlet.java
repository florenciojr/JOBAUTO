package controller;

import dao.Usuarios;
import model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Usuarios usuariosDao;

    public LoginServlet() {
        this.usuariosDao = new Usuarios();
    }

    // Construtor para injetar o DAO no teste
    public LoginServlet(Usuarios usuariosDao) {
        this.usuariosDao = usuariosDao;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = usuariosDao.autenticarUsuario(email, senha);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Email ou senha inválidos");
            request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
        }
    }
}
