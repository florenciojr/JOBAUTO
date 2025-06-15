package testesintegracao;

import dao.Usuarios;
import model.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import util.conexao;

import java.util.List;

public class UsuariosDAOTest {

    private Usuarios usuariosDAO;
    private Usuario usuarioTeste;

    // Dados de teste
    private static final String EMAIL_TESTE = "teste.integracao@example.com";
    private static final String SENHA_TESTE = "senhaTeste123";
    private static final String NOME_TESTE = "Usuário de Teste Integração";
    private static final String TELEFONE_TESTE = "11999998888";
    private static final String TIPO_TESTE = "CANDIDATO";
    private static final String FOTO_TESTE = "foto_teste.jpg";

    @Before
    public void setUp() {
        usuariosDAO = new Usuarios();
        criarUsuarioTeste();
        limparDadosTeste();
    }

    private void criarUsuarioTeste() {
        usuarioTeste = new Usuario();
        usuarioTeste.setEmail(EMAIL_TESTE);
        usuarioTeste.setSenha(SENHA_TESTE);
        usuarioTeste.setNome(NOME_TESTE);
        usuarioTeste.setTelefone(TELEFONE_TESTE);
        usuarioTeste.setTipo(TIPO_TESTE);
        usuarioTeste.setAtivo(true);
        usuarioTeste.setFotoPerfil(FOTO_TESTE);
    }

    private void limparDadosTeste() {
        Usuario existente = usuariosDAO.buscarPorEmail(EMAIL_TESTE);
        if (existente != null) {
            usuariosDAO.excluirUsuario(existente.getId());
        }
    }

    @After
    public void tearDown() {
        limparDadosTeste();
    }

    @Test
    public void testInserirUsuarioComSucesso() {
        boolean resultado = usuariosDAO.inserirUsuario(usuarioTeste);
        assertTrue("Deveria inserir com sucesso", resultado);
        assertTrue("ID deveria ser maior que zero", usuarioTeste.getId() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void testInserirUsuarioSemEmailDeveFalhar() {
        Usuario invalido = new Usuario();
        invalido.setSenha(SENHA_TESTE);
        invalido.setNome(NOME_TESTE);
        usuariosDAO.inserirUsuario(invalido);
    }

    @Test
    public void testBuscarPorIdUsuarioExistente() {
        usuariosDAO.inserirUsuario(usuarioTeste);
        int id = usuarioTeste.getId();

        Usuario encontrado = usuariosDAO.buscarPorId(id);
        assertNotNull("Deveria encontrar usuário", encontrado);
        assertEquals("Email deveria ser igual", EMAIL_TESTE, encontrado.getEmail());
    }

    @Test
    public void testBuscarPorIdUsuarioInexistente() {
        Usuario naoExistente = usuariosDAO.buscarPorId(-1);
        assertNull("Não deveria encontrar usuário", naoExistente);
    }

    @Test
    public void testListarTodosUsuarios() {
        usuariosDAO.inserirUsuario(usuarioTeste);

        List<Usuario> lista = usuariosDAO.listarTodos();
        assertFalse("Lista não deveria estar vazia", lista.isEmpty());
        assertTrue("Deveria conter o usuário de teste", 
            lista.stream().anyMatch(u -> EMAIL_TESTE.equals(u.getEmail())));
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {
        usuariosDAO.inserirUsuario(usuarioTeste);
        int id = usuarioTeste.getId();

        Usuario modificado = new Usuario();
        modificado.setId(id);
        modificado.setEmail("novo.email@teste.com");
        modificado.setNome("Nome Modificado");
        modificado.setTipo("ADMIN");

        boolean resultado = usuariosDAO.atualizarUsuario(modificado);
        assertTrue("Deveria atualizar com sucesso", resultado);

        Usuario atualizado = usuariosDAO.buscarPorId(id);
        assertEquals("Nome deveria ser atualizado", "Nome Modificado", atualizado.getNome());
    }

    @Test
    public void testExcluirUsuarioExistente() {
        usuariosDAO.inserirUsuario(usuarioTeste);
        int id = usuarioTeste.getId();

        boolean resultado = usuariosDAO.excluirUsuario(id);
        assertTrue("Deveria excluir com sucesso", resultado);

        Usuario excluido = usuariosDAO.buscarPorId(id);
        assertNull("Usuário deveria ser excluído", excluido);
    }

    @Test
    public void testBuscarPorEmailExistente() {
        usuariosDAO.inserirUsuario(usuarioTeste);

        Usuario encontrado = usuariosDAO.buscarPorEmail(EMAIL_TESTE);
        assertNotNull("Deveria encontrar por email", encontrado);
        assertEquals("Nome deveria ser igual", NOME_TESTE, encontrado.getNome());
    }

    @Test
    public void testBuscarPorTipoExistente() {
        usuariosDAO.inserirUsuario(usuarioTeste);

        List<Usuario> candidatos = usuariosDAO.buscarPorTipo(TIPO_TESTE);
        assertFalse("Deveria encontrar candidatos", candidatos.isEmpty());
        assertTrue("Deveria conter o usuário de teste",
            candidatos.stream().anyMatch(u -> EMAIL_TESTE.equals(u.getEmail())));
    }

    @Test
    public void testInserirUsuarioComEmailDuplicado() {
        usuariosDAO.inserirUsuario(usuarioTeste);

        Usuario duplicado = new Usuario();
        duplicado.setEmail(EMAIL_TESTE);
        duplicado.setSenha("outrasenha");
        duplicado.setNome("Outro Nome");

        try {
            usuariosDAO.inserirUsuario(duplicado);
            fail("Deveria lançar exceção para email duplicado");
        } catch (RuntimeException e) {
            assertTrue("Mensagem de erro correta", 
                e.getMessage().contains("Erro ao inserir usuário"));
        }
    }
}
