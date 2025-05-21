package model;

public class Plano {
    private int id;
    private String nome;
    private String descricao;
    private double valorMensal;
    private int candidaturasMes;
    private String beneficios;

    public Plano() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal; }

    public int getCandidaturasMes() { return candidaturasMes; }
    public void setCandidaturasMes(int candidaturasMes) { this.candidaturasMes = candidaturasMes; }

    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
}