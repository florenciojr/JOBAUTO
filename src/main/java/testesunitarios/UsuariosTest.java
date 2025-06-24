package testesunitarios;

import dao.Usuarios;
import model.Usuario;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import util.conexao;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuariosTest {

    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockStmt;
    @Mock private ResultSet mockRs;
    @Mock private ResultSet generatedKeys;

    private Usuarios usuariosDAO;
    private Usuario usuarioTeste;

    private static final int ID_TESTE = 30;
    private static final String EMAIL_TESTE = "testeeiii@example.com";
    private static final String SENHA_TESTE = "senha123";
    private static final String TIPO_TESTE = "CANDIDATO";
    private static final String NOME_TESTE = "Test User";
    private static final String TELEFONE_TESTE = "123456789";
    private static final String FOTO_PERFIL_TESTE = "foto.jpg";

    @Before
    public void setUp() throws SQLException {
        usuariosDAO = new Usuarios();
        usuarioTeste = new Usuario();
        usuarioTeste.setId(ID_TESTE);
        usuarioTeste.setEmail(EMAIL_TESTE);
        usuarioTeste.setSenha(SENHA_TESTE);
        usuarioTeste.setTipo(TIPO_TESTE);
        usuarioTeste.setNome(NOME_TESTE);
        usuarioTeste.setTelefone(TELEFONE_TESTE);
        usuarioTeste.setAtivo(true);
        usuarioTeste.setFotoPerfil(FOTO_PERFIL_TESTE);

        conexao.setTestConnection(mockConn);
        when(mockConn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockStmt);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
    }

    @After
    public void tearDown() {
        conexao.resetTestConnection();
    }

    @Test
    public void testInserirUsuario_Sucesso() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(1);
        when(mockStmt.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(ID_TESTE);

        boolean resultado = usuariosDAO.inserirUsuario(usuarioTeste);

        assertTrue(resultado);
        assertEquals(ID_TESTE, usuarioTeste.getId());

        verify(mockStmt).setString(eq(1), eq(EMAIL_TESTE));
        verify(mockStmt).setString(eq(2), eq(SENHA_TESTE));
        verify(mockStmt).setString(eq(3), eq(TIPO_TESTE));
        verify(mockStmt).setString(eq(4), eq(NOME_TESTE));
        verify(mockStmt).setString(eq(5), eq(TELEFONE_TESTE));
        verify(mockStmt).setString(eq(6), anyString());  // <-- ESSA É A CORREÇÃO
        verify(mockStmt).setBoolean(eq(7), eq(true));
        verify(mockStmt).setString(eq(8), eq(FOTO_PERFIL_TESTE));
    }

    @Test(expected = RuntimeException.class)
    public void testInserirUsuario_ErroSQL() throws Exception {
        when(mockConn.prepareStatement(anyString(), anyInt()))
            .thenThrow(new SQLException("Erro simulado"));

        usuariosDAO.inserirUsuario(usuarioTeste);
    }

    @Test
    public void testInserirUsuario_Falha() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(0);

        boolean resultado = usuariosDAO.inserirUsuario(usuarioTeste);

        assertFalse(resultado);
    }

    @Test
    public void testBuscarPorId_Encontrado() throws Exception {
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("id")).thenReturn(ID_TESTE);
        when(mockRs.getString("email")).thenReturn(EMAIL_TESTE);
        when(mockRs.getString("nome")).thenReturn(NOME_TESTE);
        when(mockRs.getString("tipo")).thenReturn(TIPO_TESTE);
        when(mockRs.getString("telefone")).thenReturn(TELEFONE_TESTE);
        when(mockRs.getBoolean("ativo")).thenReturn(true);
        when(mockRs.getString("foto_perfil")).thenReturn(FOTO_PERFIL_TESTE);

        Usuario usuario = usuariosDAO.buscarPorId(ID_TESTE);

        assertNotNull(usuario);
        assertEquals(ID_TESTE, usuario.getId());
        assertEquals(EMAIL_TESTE, usuario.getEmail());
    }

    @Test
    public void testBuscarPorId_NaoEncontrado() throws Exception {
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        Usuario usuario = usuariosDAO.buscarPorId(999);

        assertNull(usuario);
    }

    @Test
    public void testAtualizarUsuario_Sucesso() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(1);

        boolean resultado = usuariosDAO.atualizarUsuario(usuarioTeste);

        assertTrue(resultado);
        verify(mockStmt).setInt(eq(8), eq(ID_TESTE));
    }

    @Test
    public void testAtualizarUsuario_Falha() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(0);

        boolean resultado = usuariosDAO.atualizarUsuario(usuarioTeste);

        assertFalse(resultado);
    }

    @Test
    public void testExcluirUsuario_Sucesso() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(1);

        boolean resultado = usuariosDAO.excluirUsuario(ID_TESTE);

        assertTrue(resultado);
        verify(mockStmt).setInt(eq(1), eq(ID_TESTE));
    }

    @Test
    public void testExcluirUsuario_Falha() throws Exception {
        when(mockStmt.executeUpdate()).thenReturn(0);

        boolean resultado = usuariosDAO.excluirUsuario(ID_TESTE);

        assertFalse(resultado);
    }

    @Test
    public void testBuscarPorEmail_Encontrado() throws Exception {
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("id")).thenReturn(ID_TESTE);
        when(mockRs.getString("email")).thenReturn(EMAIL_TESTE);
        when(mockRs.getString("nome")).thenReturn(NOME_TESTE);
        when(mockRs.getString("tipo")).thenReturn(TIPO_TESTE);

        Usuario usuario = usuariosDAO.buscarPorEmail(EMAIL_TESTE);

        assertNotNull(usuario);
        assertEquals(EMAIL_TESTE, usuario.getEmail());
    }

    @Test
    public void testBuscarPorTipo_ComResultados() throws Exception {
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("id")).thenReturn(1, 2);
        when(mockRs.getString("nome")).thenReturn("User 1", "User 2");
        when(mockRs.getString("tipo")).thenReturn(TIPO_TESTE, TIPO_TESTE);

        List<Usuario> usuarios = usuariosDAO.buscarPorTipo(TIPO_TESTE);

        assertEquals(2, usuarios.size());
        assertEquals(TIPO_TESTE, usuarios.get(0).getTipo());
    }
}
