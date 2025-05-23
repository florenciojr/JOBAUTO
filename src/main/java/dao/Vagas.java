package dao;

import model.Vaga;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vagas {
    
    public boolean inserir(Vaga vaga) {
        String sql = "INSERT INTO vaga (empresa_id, titulo, descricao, requisitos, beneficios, salario, " +
                     "tipo_contrato, modalidade, localizacao, data_publicacao, data_encerramento, " +
                     "status, valor_recrutamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, vaga.getEmpresaId());
            stmt.setString(2, vaga.getTitulo());
            stmt.setString(3, vaga.getDescricao());
            stmt.setString(4, vaga.getRequisitos());
            stmt.setString(5, vaga.getBeneficios());
            stmt.setString(6, vaga.getSalario());
            stmt.setString(7, vaga.getTipoContrato());
            stmt.setString(8, vaga.getModalidade());
            stmt.setString(9, vaga.getLocalizacao());
            stmt.setString(10, vaga.getDataPublicacao());
            stmt.setString(11, vaga.getDataEncerramento());
            stmt.setString(12, vaga.getStatus());
            stmt.setDouble(13, vaga.getValorRecrutamento());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vaga.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir vaga: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Vaga buscarPorId(int id) {
        String sql = "SELECT * FROM vaga WHERE id = ?";
        Vaga vaga = null;
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                vaga = criarVagaAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vaga por ID: " + e.getMessage());
        }
        return vaga;
    }

    public List<Vaga> listarTodas() {
        String sql = "SELECT * FROM vaga";
        List<Vaga> vagas = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                vagas.add(criarVagaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vagas: " + e.getMessage());
        }
        return vagas;
    }

    public boolean atualizar(Vaga vaga) {
        String sql = "UPDATE vaga SET empresa_id = ?, titulo = ?, descricao = ?, requisitos = ?, " +
                     "beneficios = ?, salario = ?, tipo_contrato = ?, modalidade = ?, localizacao = ?, " +
                     "data_publicacao = ?, data_encerramento = ?, status = ?, valor_recrutamento = ? " +
                     "WHERE id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, vaga.getEmpresaId());
            stmt.setString(2, vaga.getTitulo());
            stmt.setString(3, vaga.getDescricao());
            stmt.setString(4, vaga.getRequisitos());
            stmt.setString(5, vaga.getBeneficios());
            stmt.setString(6, vaga.getSalario());
            stmt.setString(7, vaga.getTipoContrato());
            stmt.setString(8, vaga.getModalidade());
            stmt.setString(9, vaga.getLocalizacao());
            stmt.setString(10, vaga.getDataPublicacao());
            stmt.setString(11, vaga.getDataEncerramento());
            stmt.setString(12, vaga.getStatus());
            stmt.setDouble(13, vaga.getValorRecrutamento());
            stmt.setInt(14, vaga.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar vaga: " + e.getMessage());
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM vaga WHERE id = ?";
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar vaga: " + e.getMessage());
        }
        return false;
    }

    public List<Vaga> buscarPorTitulo(String titulo) {
        String sql = "SELECT * FROM vaga WHERE titulo LIKE ?";
        List<Vaga> vagas = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                vagas.add(criarVagaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vagas por título: " + e.getMessage());
        }
        return vagas;
    }

    public List<Vaga> buscarPorEmpresa(int empresaId) {
        String sql = "SELECT * FROM vaga WHERE empresa_id = ?";
        List<Vaga> vagas = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                vagas.add(criarVagaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vagas por empresa: " + e.getMessage());
        }
        return vagas;
    }

    public List<Vaga> buscarAtivas() {
        String sql = "SELECT * FROM vaga WHERE status = 'ABERTA'";
        List<Vaga> vagas = new ArrayList<>();
        
        try (Connection conexao = util.conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                vagas.add(criarVagaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vagas ativas: " + e.getMessage());
        }
        return vagas;
    }

    private Vaga criarVagaAPartirResultSet(ResultSet rs) throws SQLException {
        Vaga vaga = new Vaga();
        vaga.setId(rs.getInt("id"));
        vaga.setEmpresaId(rs.getInt("empresa_id"));
        vaga.setTitulo(rs.getString("titulo"));
        vaga.setDescricao(rs.getString("descricao"));
        vaga.setRequisitos(rs.getString("requisitos"));
        vaga.setBeneficios(rs.getString("beneficios"));
        vaga.setSalario(rs.getString("salario"));
        vaga.setTipoContrato(rs.getString("tipo_contrato"));
        vaga.setModalidade(rs.getString("modalidade"));
        vaga.setLocalizacao(rs.getString("localizacao"));
        vaga.setDataPublicacao(rs.getString("data_publicacao"));
        vaga.setDataEncerramento(rs.getString("data_encerramento"));
        vaga.setStatus(rs.getString("status"));
        vaga.setValorRecrutamento(rs.getDouble("valor_recrutamento"));
        return vaga;
    }
}