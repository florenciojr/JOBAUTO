package model;


public class PerfilCandidato {
    private int usuarioId;
    private String profissao;
    private String resumo;
    private String habilidades;
    private String experiencia;
    private String pretensaoSalarial;

    public PerfilCandidato() {}

    // Getters e Setters
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getPretensaoSalarial() { return pretensaoSalarial; }
    public void setPretensaoSalarial(String pretensaoSalarial) { this.pretensaoSalarial = pretensaoSalarial; }
}