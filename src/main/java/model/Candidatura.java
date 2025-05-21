package model;

public class Candidatura {
    private int id;
    private int vagaId;
    private int candidatoId;
    private String dataCandidatura;
    private String tipo;
    private String status;

    public Candidatura() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVagaId() { return vagaId; }
    public void setVagaId(int vagaId) { this.vagaId = vagaId; }

    public int getCandidatoId() { return candidatoId; }
    public void setCandidatoId(int candidatoId) { this.candidatoId = candidatoId; }

    public String getDataCandidatura() { return dataCandidatura; }
    public void setDataCandidatura(String dataCandidatura) { this.dataCandidatura = dataCandidatura; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}