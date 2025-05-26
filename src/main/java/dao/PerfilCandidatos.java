package dao;

import model.PerfilCandidato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilCandidatos {
    
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
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir perfil: " + e.getMessage());
            return false;
        }
    }
    
    public boolean atualizarPerfil(PerfilCandidato perfil) {
        String sql = "UPDATE perfil_candidato SET profissao=?, resumo=?, habilidades=?, experiencia=?, pretensao_salarial=? WHERE usuario_id=?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, perfil.getProfissao());
            stmt.setString(2, perfil.getResumo());
            stmt.setString(3, perfil.getHabilidades());
            stmt.setString(4, perfil.getExperiencia());
            stmt.setString(5, perfil.getPretensaoSalarial());
            stmt.setInt(6, perfil.getUsuarioId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar perfil: " + e.getMessage());
            return false;
        }
    }
    
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
            System.err.println("Erro ao buscar perfil: " + e.getMessage());
        }
        return perfil;
    }
    
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
            System.err.println("Erro ao listar perfis: " + e.getMessage());
        }
        return perfis;
    }
    
    public boolean excluirPerfil(int usuarioId) {
        String sql = "DELETE FROM perfil_candidato WHERE usuario_id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir perfil: " + e.getMessage());
            return false;
        }
    }
    
    
    
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
            System.err.println("Erro ao buscar por profissão: " + e.getMessage());
        }
        return perfis;
    }
}