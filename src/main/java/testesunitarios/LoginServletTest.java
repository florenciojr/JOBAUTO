
package testesunitarios;

import controller.LoginServlet;
import dao.Usuarios;
import model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;
    @Mock private Usuarios usuariosDao;

    @InjectMocks
    private LoginServlet loginServlet;

    private static final String EMAIL_VALIDO = "usuario@teste.com";
    private static final String SENHA_VALIDA = "senha123";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        loginServlet = new LoginServlet(usuariosDao); // Injetar DAO mockado
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost_LoginValido() throws Exception {
        when(request.getParameter("email")).thenReturn(EMAIL_VALIDO);
        when(request.getParameter("senha")).thenReturn(SENHA_VALIDA);

        Usuario usuarioMock = new Usuario();
        when(usuariosDao.autenticarUsuario(EMAIL_VALIDO, SENHA_VALIDA)).thenReturn(usuarioMock);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("usuarioLogado", usuarioMock);
        verify(dispatcher).forward(request, response);
        verify(request, never()).setAttribute(eq("mensagem"), anyString());
    }

    @Test
    public void testDoPost_LoginInvalido() throws Exception {
        when(request.getParameter("email")).thenReturn(EMAIL_VALIDO);
        when(request.getParameter("senha")).thenReturn("senhaErrada");

        when(usuariosDao.autenticarUsuario(EMAIL_VALIDO, "senhaErrada")).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(request).setAttribute(eq("mensagem"), eq("Email ou senha inválidos"));
        verify(dispatcher).forward(request, response);
        verify(session, never()).setAttribute(eq("usuarioLogado"), any());
    }

    @Test
    public void testDoGet_SessionValida() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("usuarioLogado")).thenReturn(new Usuario());

        loginServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_SessionInvalida() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        loginServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
