package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuarios {
    
    // Método para inserir um novo usuário
    public boolean inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (email, senha, tipo, nome, telefone, data_cadastro, ativo, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getDataCadastro());
            stmt.setBoolean(7, usuario.isAtivo());
            stmt.setString(8, usuario.getFotoPerfil());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
        return false;
    }
    
    // Método para buscar um usuário por ID
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;
        
        try (Connection conexao =util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
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
            System.out.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return usuario;
    }
    
    // Método para buscar todos os usuários
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
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
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }
    
    // Método para atualizar um usuário
    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET email = ?, senha = ?, tipo = ?, nome = ?, telefone = ?, ativo = ?, foto_perfil = ? WHERE id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
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
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        return false;
    }
    
    // Método para excluir um usuário
    public boolean excluirUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
        return false;
    }
    
    // Método para buscar usuário por email (útil para login)
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Usuario usuario = null;
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
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
            System.out.println("Erro ao buscar usuário por email: " + e.getMessage());
        }
        return usuario;
    }
}