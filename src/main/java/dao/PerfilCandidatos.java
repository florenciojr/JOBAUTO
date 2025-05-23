package dao;

import model.PerfilCandidato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilCandidatos {
    
    // Método para inserir um novo perfil de candidato
    public boolean inserirPerfil(PerfilCandidato perfil) {
        String sql = "INSERT INTO perfil_candidato (usuario_id, profissao, resumo, habilidades, experiencia, pretensao_salarial) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, perfil.getUsuarioId());
            stmt.setString(2, perfil.getProfissao());
            stmt.setString(3, perfil.getResumo());
            stmt.setString(4, perfil.getHabilidades());
            stmt.setString(5, perfil.getExperiencia());
            stmt.setString(6, perfil.getPretensaoSalarial());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir perfil de candidato: " + e.getMessage());
        }
        return false;
    }
    
    // Método para buscar um perfil por ID de usuário
    public PerfilCandidato buscarPorUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM perfil_candidato WHERE usuario_id = ?";
        PerfilCandidato perfil = null;
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                perfil = new PerfilCandidato();
                perfil.setUsuarioId(rs.getInt("usuario_id"));
                perfil.setProfissao(rs.getString("profissao"));
                perfil.setResumo(rs.getString("resumo"));
                perfil.setHabilidades(rs.getString("habilidades"));
                perfil.setExperiencia(rs.getString("experiencia"));
                perfil.setPretensaoSalarial(rs.getString("pretensao_salarial"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar perfil por ID de usuário: " + e.getMessage());
        }
        return perfil;
    }
    
    // Método para listar todos os perfis de candidatos
    public List<PerfilCandidato> listarTodos() {
        String sql = "SELECT * FROM perfil_candidato";
        List<PerfilCandidato> perfis = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PerfilCandidato perfil = new PerfilCandidato();
                perfil.setUsuarioId(rs.getInt("usuario_id"));
                perfil.setProfissao(rs.getString("profissao"));
                perfil.setResumo(rs.getString("resumo"));
                perfil.setHabilidades(rs.getString("habilidades"));
                perfil.setExperiencia(rs.getString("experiencia"));
                perfil.setPretensaoSalarial(rs.getString("pretensao_salarial"));
                
                perfis.add(perfil);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar perfis de candidatos: " + e.getMessage());
        }
        return perfis;
    }
    
    // Método para atualizar um perfil de candidato
    public boolean atualizarPerfil(PerfilCandidato perfil) {
        String sql = "UPDATE perfil_candidato SET profissao = ?, resumo = ?, habilidades = ?, experiencia = ?, pretensao_salarial = ? WHERE usuario_id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, perfil.getProfissao());
            stmt.setString(2, perfil.getResumo());
            stmt.setString(3, perfil.getHabilidades());
            stmt.setString(4, perfil.getExperiencia());
            stmt.setString(5, perfil.getPretensaoSalarial());
            stmt.setInt(6, perfil.getUsuarioId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar perfil de candidato: " + e.getMessage());
        }
        return false;
    }
    
    // Método para excluir um perfil de candidato
    public boolean excluirPerfil(int usuarioId) {
        String sql = "DELETE FROM perfil_candidato WHERE usuario_id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir perfil de candidato: " + e.getMessage());
        }
        return false;
    }
    
    // Método para buscar perfis por profissão (similar ao buscar por email do usuário)
    public List<PerfilCandidato> buscarPorProfissao(String profissao) {
        String sql = "SELECT * FROM perfil_candidato WHERE profissao LIKE ?";
        List<PerfilCandidato> perfis = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + profissao + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PerfilCandidato perfil = new PerfilCandidato();
                perfil.setUsuarioId(rs.getInt("usuario_id"));
                perfil.setProfissao(rs.getString("profissao"));
                perfil.setResumo(rs.getString("resumo"));
                perfil.setHabilidades(rs.getString("habilidades"));
                perfil.setExperiencia(rs.getString("experiencia"));
                perfil.setPretensaoSalarial(rs.getString("pretensao_salarial"));
                
                perfis.add(perfil);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar perfis por profissão: " + e.getMessage());
        }
        return perfis;
    }
}