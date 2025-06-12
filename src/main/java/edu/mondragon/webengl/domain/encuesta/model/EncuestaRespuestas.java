package edu.mondragon.webengl.domain.encuesta.model;


public class EncuestaRespuestas
{
    // Psicológico
    private Integer psicologico;          // PSI_1
       // PSI_4

    // Lingüístico
    private Integer linguistico;           // LIN_1
        // LIN_4

    // Económico
    private Integer economico;             // ECO_1
         // ECO_4

    // Político
    private Integer politico;          // POL_1
  // POL_4_11

    // Social
    private Integer social;          // SOC_1
    // SOC_4

    // Navegacional
    private Integer navegacional;     // NAV_1

    public Integer getPsicologico() {
        return psicologico;
    }
    public void sumPsicologico(Integer psicologico) {
        if (this.psicologico == null) {
            this.psicologico = psicologico;
        } else {
            this.psicologico += psicologico;
        }
    }
    public void setPsicologico(Integer psicologico) {
        this.psicologico = psicologico;
    }

    public Integer getLinguistico() {
        return linguistico;
    }

    public void setLinguistico(Integer linguistico) {
        this.linguistico = linguistico;
    }

    public Integer getEconomico() {
        return economico;
    }

    public void setEconomico(Integer economico) {
        this.economico = economico;
    }

    public Integer getPolitico() {
        return politico;
    }

    public void setPolitico(Integer politico) {
        this.politico = politico;
    }

    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public Integer getNavegacional() {
        return navegacional;
    }

    public void setNavegacional(Integer navegacional) {
        this.navegacional = navegacional;
    }
    public void sumLinguistico(Integer linguistico) {
        if (this.linguistico == null) {
            this.linguistico = linguistico;
        } else {
            this.linguistico += linguistico;
        }
    }
    public void sumEconomico(Integer economico) {
        if (this.economico == null) {
            this.economico = economico;
        } else {
            this.economico += economico;
        }
    }
    public void sumPolitico(Integer politico) {
        if (this.politico == null) {
            this.politico = politico;
        } else {
            this.politico += politico;
        }
    }
    public void sumSocial(Integer social) {
        if (this.social == null) {
            this.social = social;
        } else {
            this.social += social;
        }
    }
    public void sumNavegacional(Integer navegacional) {
        if (this.navegacional == null) {
            this.navegacional = navegacional;
        } else {
            this.navegacional += navegacional;
        }
    }


 
}