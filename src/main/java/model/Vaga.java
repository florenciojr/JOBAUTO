package model;

public class Vaga {
    private int id;
    private int empresaId;
    private String titulo;
    private String descricao;
    private String requisitos;
    private String beneficios;
    private String salario;
    private String tipoContrato;
    private String modalidade;
    private String localizacao;
    private String dataPublicacao;
    private String dataEncerramento;
    private String status;
    private double valorRecrutamento;

    public Vaga() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }

    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }

    public String getSalario() { return salario; }
    public void setSalario(String salario) { this.salario = salario; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(String dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    public String getDataEncerramento() { return dataEncerramento; }
    public void setDataEncerramento(String dataEncerramento) { this.dataEncerramento = dataEncerramento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getValorRecrutamento() { return valorRecrutamento; }
    public void setValorRecrutamento(double valorRecrutamento) { this.valorRecrutamento = valorRecrutamento; }
}