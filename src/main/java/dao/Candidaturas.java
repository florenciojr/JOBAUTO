package dao;

import model.Candidatura;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Candidaturas {
    private Connection conexao;

    public Candidaturas() {
        this.conexao = util.conexao.getConexao();
    }

    // Método para inserir uma nova candidatura
    public boolean inserir(Candidatura candidatura) {
        String sql = "INSERT INTO candidatura (vaga_id, candidato_id, data_candidatura, tipo, status) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Validar os valores ENUM
            String tipo = validarTipo(candidatura.getTipo());
            String status = validarStatus(candidatura.getStatus());
            
            stmt.setInt(1, candidatura.getVagaId());
            stmt.setInt(2, candidatura.getCandidatoId());
            stmt.setTimestamp(3, candidatura.getDataCandidatura() != null ? 
                Timestamp.valueOf(candidatura.getDataCandidatura()) : null);
            stmt.setString(4, tipo);
            stmt.setString(5, status);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        candidatura.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir candidatura: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro nos dados da candidatura: " + e.getMessage());
        }
        return false;
    }

    // Buscar candidatura por ID
    public Candidatura buscarPorId(int id) {
        String sql = "SELECT * FROM candidatura WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarCandidaturaAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidatura por ID: " + e.getMessage());
        }
        return null;
    }

    // Listar todas as candidaturas
    public List<Candidatura> listarTodas() {
        String sql = "SELECT * FROM candidatura";
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar candidaturas: " + e.getMessage());
        }
        return candidaturas;
    }

    // Atualizar candidatura
    public boolean atualizar(Candidatura candidatura) {
        String sql = "UPDATE candidatura SET vaga_id = ?, candidato_id = ?, data_candidatura = ?, " +
                     "tipo = ?, status = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            String tipo = validarTipo(candidatura.getTipo());
            String status = validarStatus(candidatura.getStatus());
            
            stmt.setInt(1, candidatura.getVagaId());
            stmt.setInt(2, candidatura.getCandidatoId());
            stmt.setTimestamp(3, candidatura.getDataCandidatura() != null ? 
                Timestamp.valueOf(candidatura.getDataCandidatura()) : null);
            stmt.setString(4, tipo);
            stmt.setString(5, status);
            stmt.setInt(6, candidatura.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar candidatura: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro nos dados da candidatura: " + e.getMessage());
        }
        return false;
    }

    // Deletar candidatura
    public boolean deletar(int id) {
        String sql = "DELETE FROM candidatura WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar candidatura: " + e.getMessage());
        }
        return false;
    }

    // Buscar candidaturas por vaga
    public List<Candidatura> buscarPorVaga(int vagaId) {
        String sql = "SELECT * FROM candidatura WHERE vaga_id = ?";
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, vagaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidaturas por vaga: " + e.getMessage());
        }
        return candidaturas;
    }

    // Buscar candidaturas por candidato
    public List<Candidatura> buscarPorCandidato(int candidatoId) {
        String sql = "SELECT * FROM candidatura WHERE candidato_id = ?";
        List<Candidatura> candidaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, candidatoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidaturas.add(criarCandidaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidaturas por candidato: " + e.getMessage());
        }
        return candidaturas;
    }

    // Métodos auxiliares
    private String validarTipo(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo não pode ser nulo");
        }
        if (!tipo.equals("AUTOMATICA") && !tipo.equals("MANUAL")) {
            throw new IllegalArgumentException("Tipo inválido: " + tipo + ". Valores permitidos: AUTOMATICA, MANUAL");
        }
        return tipo;
    }

    private String validarStatus(String status) {
        if (status == null) return "PENDENTE";
        
        if (!status.equals("PENDENTE") && !status.equals("VISUALIZADA") && 
            !status.equals("ENTREVISTA") && !status.equals("CONTRATADO") && 
            !status.equals("REJEITADA")) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
        return status;
    }

    private Candidatura criarCandidaturaAPartirResultSet(ResultSet rs) throws SQLException {
        Candidatura candidatura = new Candidatura();
        candidatura.setId(rs.getInt("id"));
        candidatura.setVagaId(rs.getInt("vaga_id"));
        candidatura.setCandidatoId(rs.getInt("candidato_id"));
        candidatura.setDataCandidatura(rs.getTimestamp("data_candidatura") != null ? 
            rs.getTimestamp("data_candidatura").toLocalDateTime().toString() : null);
        candidatura.setTipo(rs.getString("tipo"));
        candidatura.setStatus(rs.getString("status"));
        return candidatura;
    }

    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}