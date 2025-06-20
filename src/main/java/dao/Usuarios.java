package dao;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.conexao;

public class Usuarios {
    
	public boolean inserirUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuario (email, senha, tipo, nome, telefone, data_cadastro, ativo, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet generatedKeys = null;
    
    try {
        conn = conexao.getConexao();
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, usuario.getEmail());
        stmt.setString(2, usuario.getSenha());
        stmt.setString(3, usuario.getTipo());
        stmt.setString(4, usuario.getNome());
        stmt.setString(5, usuario.getTelefone());
        
        // Formato de data compatível com MySQL (YYYY-MM-DD HH:MM:SS)
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = sdf.format(new java.util.Date());
        stmt.setString(6, dataFormatada);
        
        stmt.setBoolean(7, usuario.isAtivo());
        stmt.setString(8, usuario.getFotoPerfil());
        
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows > 0) {
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                usuario.setId(generatedKeys.getInt(1));
            }
            return true;
        }
        return false;
    } catch (SQLException e) {
        // Lança RuntimeException como esperado nos testes
        throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
    } finally {
        try {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
}
        
        
        
        
        
    
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setDataCadastro(rs.getString("data_cadastro"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setFotoPerfil(rs.getString("foto_perfil"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return usuario;
    }
    
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setDataCadastro(rs.getString("data_cadastro"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setFotoPerfil(rs.getString("foto_perfil"));
                
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return usuarios;
    }
    
    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET email = ?, senha = ?, tipo = ?, nome = ?, telefone = ?, ativo = ?, foto_perfil = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getTelefone());
            stmt.setBoolean(6, usuario.isAtivo());
            stmt.setString(7, usuario.getFotoPerfil());
            stmt.setInt(8, usuario.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }
    
    public boolean excluirUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(stmt, conn);
        }
        return false;
    }
    
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conn = conexao.getConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setDataCadastro(rs.getString("data_cadastro"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setFotoPerfil(rs.getString("foto_perfil"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.fecharRecursos(rs, stmt, conn);
        }
        return usuario;
    }
    
    public List<Usuario> buscarPorTipo(String tipo) {
        String sql = "SELECT id, nome, email, telefone FROM usuario WHERE tipo = ?";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, tipo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setTipo(tipo); // Já sabemos que é do tipo solicitado
                    
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários por tipo '" + tipo + "': " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
        
        return usuarios;
    }
    
    
    public Usuario autenticarUsuario(String email, String senha) {
    String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ? AND ativo = true";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Usuario usuario = null;
    
    try {
        conn = conexao.getConexao();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senha);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipo(rs.getString("tipo"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setDataCadastro(rs.getString("data_cadastro"));
            usuario.setAtivo(rs.getBoolean("ativo"));
            usuario.setFotoPerfil(rs.getString("foto_perfil"));
        }
    } catch (SQLException e) {
        System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        e.printStackTrace();
    } finally {
        conexao.fecharRecursos(rs, stmt, conn);
    }
    return usuario;
}
    
    
}
