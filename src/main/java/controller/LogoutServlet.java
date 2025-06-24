package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Invalida a sessão atual
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Remove todos os atributos da sessão
            session.invalidate();
        }
        
        // Redireciona para a página de login com mensagem de sucesso
        response.sendRedirect(request.getContextPath() + "/login?logout=true");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Chama o mesmo método do GET para garantir consistência
        doGet(request, response);
    }
}
