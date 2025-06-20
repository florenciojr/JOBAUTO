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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        Usuarios usuariosDao = new Usuarios();
        Usuario usuario = usuariosDao.autenticarUsuario(email, senha);
        
        if (usuario != null) {
            // Cria a sessão
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            
            // Redireciona diretamente para o index.jsp dentro de WEB-INF/views/
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Email ou senha inválidos");
            request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Se já estiver logado, mostra o index.jsp diretamente
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login/login.jsp").forward(request, response);
        }
    }
}
