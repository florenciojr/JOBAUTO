package model;

public class Assinatura {
    private int id;
    private int candidatoId;
    private int planoId;
    private String dataInicio;
    private String dataFim;
    private boolean ativa;

    public Assinatura() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCandidatoId() { return candidatoId; }
    public void setCandidatoId(int candidatoId) { this.candidatoId = candidatoId; }

    public int getPlanoId() { return planoId; }
    public void setPlanoId(int planoId) { this.planoId = planoId; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
}