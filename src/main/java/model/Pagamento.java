package model;

public class Pagamento {
    private int id;
    private int usuarioId;
    private double valor;
    private String dataPagamento;
    private String metodo;
    private String status; // "PENDENTE", "APROVADO" ou "RECUSADO"
    private String descricao;

    // Construtor vazio
    public Pagamento() {}

    // Construtor com campos obrigatórios
    public Pagamento(int usuarioId, double valor, String status) {
        this.usuarioId = usuarioId;
        this.valor = valor;
        this.status = status;
    }

    // Getters e Setters completos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método toString para facilitar a visualização
    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", valor=" + valor +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", metodo='" + metodo + '\'' +
                ", status='" + status + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}