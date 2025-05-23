package dao;



import model.Assinatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Assinaturas {
    private Connection conexao;

    public Assinaturas() {
        this.conexao = util.conexao.getConexao();
    }

    // Método principal para criar assinatura
    public boolean criar(Assinatura assinatura) {
        if (!validarDadosAssinatura(assinatura)) {
            return false;
        }

        String sql = "INSERT INTO assinatura (candidato_id, plano_id, data_inicio, data_fim, ativa) " +
                   "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, assinatura.getCandidatoId());
            stmt.setInt(2, assinatura.getPlanoId());
            stmt.setString(3, assinatura.getDataInicio());
            stmt.setString(4, assinatura.getDataFim());
            stmt.setBoolean(5, assinatura.isAtiva());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        assinatura.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar assinatura: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Validação completa dos dados
    private boolean validarDadosAssinatura(Assinatura assinatura) {
        if (assinatura.getCandidatoId() <= 0) {
            System.err.println("ID do candidato inválido!");
            return false;
        }

        if (assinatura.getPlanoId() <= 0) {
            System.err.println("ID do plano inválido!");
            return false;
        }

        if (!candidatoExiste(assinatura.getCandidatoId())) {
            System.err.println("Candidato ID " + assinatura.getCandidatoId() + " não existe!");
            return false;
        }

        if (!planoExiste(assinatura.getPlanoId())) {
            System.err.println("Plano ID " + assinatura.getPlanoId() + " não existe!");
            return false;
        }

        if (assinatura.getDataInicio() == null || assinatura.getDataInicio().isEmpty()) {
            System.err.println("Data de início é obrigatória!");
            return false;
        }

        return true;
    }

    // Verifica se candidato existe e é do tipo correto
    private boolean candidatoExiste(int candidatoId) {
        String sql = "SELECT id FROM usuario WHERE id = ? AND tipo = 'CANDIDATO'";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, candidatoId);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar candidato: " + e.getMessage());
            return false;
        }
    }

    // Verifica se plano existe
    private boolean planoExiste(int planoId) {
        String sql = "SELECT id FROM plano WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, planoId);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar plano: " + e.getMessage());
            return false;
        }
    }

    // Busca assinatura por ID
    public Assinatura buscarPorId(int id) {
        String sql = "SELECT * FROM assinatura WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarAssinaturaAPartirResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar assinatura por ID: " + e.getMessage());
        }
        return null;
    }

    // Lista todas as assinaturas
    public List<Assinatura> listarTodas() {
        String sql = "SELECT * FROM assinatura";
        List<Assinatura> assinaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                assinaturas.add(criarAssinaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar assinaturas: " + e.getMessage());
        }
        return assinaturas;
    }

    // Atualiza assinatura
    public boolean atualizar(Assinatura assinatura) {
        if (!validarDadosAssinatura(assinatura)) {
            return false;
        }

        String sql = "UPDATE assinatura SET candidato_id = ?, plano_id = ?, data_inicio = ?, " +
                   "data_fim = ?, ativa = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, assinatura.getCandidatoId());
            stmt.setInt(2, assinatura.getPlanoId());
            stmt.setString(3, assinatura.getDataInicio());
            stmt.setString(4, assinatura.getDataFim());
            stmt.setBoolean(5, assinatura.isAtiva());
            stmt.setInt(6, assinatura.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar assinatura: " + e.getMessage());
        }
        return false;
    }

    // Cancela assinatura (marca como inativa)
    public boolean cancelar(int id) {
        String sql = "UPDATE assinatura SET ativa = false WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar assinatura: " + e.getMessage());
        }
        return false;
    }

    // Busca assinaturas por candidato
    public List<Assinatura> buscarPorCandidato(int candidatoId) {
        String sql = "SELECT * FROM assinatura WHERE candidato_id = ?";
        List<Assinatura> assinaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, candidatoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                assinaturas.add(criarAssinaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar assinaturas por candidato: " + e.getMessage());
        }
        return assinaturas;
    }

    // Busca assinaturas ativas
    public List<Assinatura> buscarAtivas() {
        String sql = "SELECT * FROM assinatura WHERE ativa = true";
        List<Assinatura> assinaturas = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                assinaturas.add(criarAssinaturaAPartirResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar assinaturas ativas: " + e.getMessage());
        }
        return assinaturas;
    }

    // Método auxiliar para criar objeto a partir do ResultSet
    private Assinatura criarAssinaturaAPartirResultSet(ResultSet rs) throws SQLException {
        Assinatura assinatura = new Assinatura();
        assinatura.setId(rs.getInt("id"));
        assinatura.setCandidatoId(rs.getInt("candidato_id"));
        assinatura.setPlanoId(rs.getInt("plano_id"));
        assinatura.setDataInicio(rs.getString("data_inicio"));
        assinatura.setDataFim(rs.getString("data_fim"));
        assinatura.setAtiva(rs.getBoolean("ativa"));
        return assinatura;
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