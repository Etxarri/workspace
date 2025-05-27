package edu.mondragon.webengl.domain.encuesta.model;



public class EncuestaRespuestas
{
    // Psicológico
    private Integer psicologicoConexion;          // PSI_1
    private Integer psicologicoExtranjero;        // PSI_2
    private Integer psicologicoDeseoVivir;        // PSI_3 (Corregido de linguisticoEscucha)
    private Integer psicologicoAislamiento;       // PSI_4

    // Lingüístico
    private Integer linguisticoLectura;           // LIN_1
    private Integer linguisticoConversacion;      // LIN_2
    private Integer linguisticoEscritura;         // LIN_3
    private Integer linguisticoEscucha;           // LIN_4

    // Económico
    private Integer economicoIngreso;             // ECO_1
    private Integer economicoSituacion;           // ECO_2
    private Integer economicoGasto400;            // ECO_3_400
    private Integer economicoGasto800;            // ECO_3_800
    private Integer economicoGasto8000;           // ECO_3_8000
    private Integer economicoGasto40000;          // ECO_3_40000
    private Integer economicoSatisfaccion;        // ECO_4

    // Político
    private Integer politicoComprension;          // POL_1
    private Integer politicoDiscusion;            // POL_2
    private Integer politicoPartidos;             // POL_3_1
    private Integer politicoPresidentePartido;    // POL_3_2
    private Integer politicoSenadoPartido;        // POL_3_3
    private String politicoEdadVoto;              // POL_3_4 (Select)
    private Integer politicoAccionConvencer;      // POL_4_1
    private Integer politicoAccionInfluirVoto;    // POL_4_2
    private Integer politicoAccionDeclaracion;    // POL_4_3
    private Integer politicoAccionDiscusionPublica; // POL_4_4
    private Integer politicoAccionContacto;       // POL_4_5
    private Integer politicoAccionTrabajoPartido; // POL_4_6
    private Integer politicoAccionInsignia;       // POL_4_7
    private Integer politicoAccionFirmaPeticion;  // POL_4_8
    private Integer politicoAccionManifestacion;  // POL_4_9
    private Integer politicoAccionBoicot;         // POL_4_10
    private Integer politicoAccionRecogerFirmas;  // POL_4_11

    // Social
    private Integer socialCenaEspanoles;          // SOC_1
    private Integer socialContactosEspanoles;     // SOC_2
    private Integer socialParticipacionGrupoA;     // SOC_3_1A
    private Integer socialParticipacionGrupoB;     // SOC_3_1B
    private Integer socialParticipacionGrupoC;     // SOC_3_1C
    private Integer socialParticipacionGrupoD;     // SOC_3_1D
    private Integer socialParticipacionGrupoE;     // SOC_3_1E
    private Integer socialMiembrosEspanolesGrupoA; // SOC_3_2A
    private Integer socialMiembrosEspanolesGrupoB; // SOC_3_2B
    private Integer socialMiembrosEspanolesGrupoC; // SOC_3_2C
    private Integer socialMiembrosEspanolesGrupoD; // SOC_3_2D
    private Integer socialMiembrosEspanolesGrupoE; // SOC_3_2E
    private Integer socialFavoresEspanoles;       // SOC_4

    // Navegacional
    private Integer navegacionalConsultaMedica;     // NAV_1
    private Integer navegacionalBuscarEmpleo;       // NAV_2
    private Integer navegacionalAyudaLegal;         // NAV_3
    private Integer navegacionalConduccionAlcohol;  // NAV_4_1
    private Integer navegacionalPagoImpuestos;      // NAV_4_2
    private Integer navegacionalFormatoDireccion;   // NAV_4_3
    private Integer navegacionalAyudaMedicaCronica; // NAV_4_4


    //Getters y setters
    public Integer getPsicologicoConexion() {
        return psicologicoConexion;
    }

    public void setPsicologicoConexion(Integer psicologicoConexion) {
        this.psicologicoConexion = psicologicoConexion;
    }

    public Integer getPsicologicoExtranjero() {
        return psicologicoExtranjero;
    }

    public void setPsicologicoExtranjero(Integer psicologicoExtranjero) {
        this.psicologicoExtranjero = psicologicoExtranjero;
    }

    public Integer getPsicologicoDeseoVivir() {
        return psicologicoDeseoVivir;
    }

    public void setPsicologicoDeseoVivir(Integer psicologicoDeseoVivir) {
        this.psicologicoDeseoVivir = psicologicoDeseoVivir;
    }

    public Integer getPsicologicoAislamiento() {
        return psicologicoAislamiento;
    }

    public void setPsicologicoAislamiento(Integer psicologicoAislamiento) {
        this.psicologicoAislamiento = psicologicoAislamiento;
    }

    public Integer getLinguisticoLectura() {
        return linguisticoLectura;
    }

    public void setLinguisticoLectura(Integer linguisticoLectura) {
        this.linguisticoLectura = linguisticoLectura;
    }

    public Integer getLinguisticoConversacion() {
        return linguisticoConversacion;
    }

    public void setLinguisticoConversacion(Integer linguisticoConversacion) {
        this.linguisticoConversacion = linguisticoConversacion;
    }

    public Integer getLinguisticoEscritura() {
        return linguisticoEscritura;
    }

    public void setLinguisticoEscritura(Integer linguisticoEscritura) {
        this.linguisticoEscritura = linguisticoEscritura;
    }

    public Integer getLinguisticoEscucha() {
        return linguisticoEscucha;
    }

    public void setLinguisticoEscucha(Integer linguisticoEscucha) {
        this.linguisticoEscucha = linguisticoEscucha;
    }

    public Integer getEconomicoIngreso() {
        return economicoIngreso;
    }

    public void setEconomicoIngreso(Integer economicoIngreso) {
        this.economicoIngreso = economicoIngreso;
    }

    public Integer getEconomicoSituacion() {
        return economicoSituacion;
    }

    public void setEconomicoSituacion(Integer economicoSituacion) {
        this.economicoSituacion = economicoSituacion;
    }

    public Integer getEconomicoGasto400() {
        return economicoGasto400;
    }

    public void setEconomicoGasto400(Integer economicoGasto400) {
        this.economicoGasto400 = economicoGasto400;
    }

    public Integer getEconomicoGasto800() {
        return economicoGasto800;
    }

    public void setEconomicoGasto800(Integer economicoGasto800) {
        this.economicoGasto800 = economicoGasto800;
    }

    public Integer getEconomicoGasto8000() {
        return economicoGasto8000;
    }

    public void setEconomicoGasto8000(Integer economicoGasto8000) {
        this.economicoGasto8000 = economicoGasto8000;
    }

    public Integer getEconomicoGasto40000() {
        return economicoGasto40000;
    }

    public void setEconomicoGasto40000(Integer economicoGasto40000) {
        this.economicoGasto40000 = economicoGasto40000;
    }

    public Integer getEconomicoSatisfaccion() {
        return economicoSatisfaccion;
    }

    public void setEconomicoSatisfaccion(Integer economicoSatisfaccion) {
        this.economicoSatisfaccion = economicoSatisfaccion;
    }

    public Integer getPoliticoComprension() {
        return politicoComprension;
    }

    public void setPoliticoComprension(Integer politicoComprension) {
        this.politicoComprension = politicoComprension;
    }

    public Integer getPoliticoDiscusion() {
        return politicoDiscusion;
    }

    public void setPoliticoDiscusion(Integer politicoDiscusion) {
        this.politicoDiscusion = politicoDiscusion;
    }

    public Integer getPoliticoPartidos() {
        return politicoPartidos;
    }

    public void setPoliticoPartidos(Integer politicoPartidos) {
        this.politicoPartidos = politicoPartidos;
    }

    public Integer getPoliticoPresidentePartido() {
        return politicoPresidentePartido;
    }

    public void setPoliticoPresidentePartido(Integer politicoPresidentePartido) {
        this.politicoPresidentePartido = politicoPresidentePartido;
    }

    public Integer getPoliticoSenadoPartido() {
        return politicoSenadoPartido;
    }

    public void setPoliticoSenadoPartido(Integer politicoSenadoPartido) {
        this.politicoSenadoPartido = politicoSenadoPartido;
    }

    public String getPoliticoEdadVoto() {
        return politicoEdadVoto;
    }

    public void setPoliticoEdadVoto(String politicoEdadVoto) {
        this.politicoEdadVoto = politicoEdadVoto;
    }

    public Integer getPoliticoAccionConvencer() {
        return politicoAccionConvencer;
    }

    public void setPoliticoAccionConvencer(Integer politicoAccionConvencer) {
        this.politicoAccionConvencer = politicoAccionConvencer;
    }

    public Integer getPoliticoAccionInfluirVoto() {
        return politicoAccionInfluirVoto;
    }

    public void setPoliticoAccionInfluirVoto(Integer politicoAccionInfluirVoto) {
        this.politicoAccionInfluirVoto = politicoAccionInfluirVoto;
    }

    public Integer getPoliticoAccionDeclaracion() {
        return politicoAccionDeclaracion;
    }

    public void setPoliticoAccionDeclaracion(Integer politicoAccionDeclaracion) {
        this.politicoAccionDeclaracion = politicoAccionDeclaracion;
    }

    public Integer getPoliticoAccionDiscusionPublica() {
        return politicoAccionDiscusionPublica;
    }

    public void setPoliticoAccionDiscusionPublica(Integer politicoAccionDiscusionPublica) {
        this.politicoAccionDiscusionPublica = politicoAccionDiscusionPublica;
    }

    public Integer getPoliticoAccionContacto() {
        return politicoAccionContacto;
    }

    public void setPoliticoAccionContacto(Integer politicoAccionContacto) {
        this.politicoAccionContacto = politicoAccionContacto;
    }

    public Integer getPoliticoAccionTrabajoPartido() {
        return politicoAccionTrabajoPartido;
    }

    public void setPoliticoAccionTrabajoPartido(Integer politicoAccionTrabajoPartido) {
        this.politicoAccionTrabajoPartido = politicoAccionTrabajoPartido;
    }

    public Integer getPoliticoAccionInsignia() {
        return politicoAccionInsignia;
    }

    public void setPoliticoAccionInsignia(Integer politicoAccionInsignia) {
        this.politicoAccionInsignia = politicoAccionInsignia;
    }

    public Integer getPoliticoAccionFirmaPeticion() {
        return politicoAccionFirmaPeticion;
    }

    public void setPoliticoAccionFirmaPeticion(Integer politicoAccionFirmaPeticion) {
        this.politicoAccionFirmaPeticion = politicoAccionFirmaPeticion;
    }

    public Integer getPoliticoAccionManifestacion() {
        return politicoAccionManifestacion;
    }

    public void setPoliticoAccionManifestacion(Integer politicoAccionManifestacion) {
        this.politicoAccionManifestacion = politicoAccionManifestacion;
    }

    public Integer getPoliticoAccionBoicot() {
        return politicoAccionBoicot;
    }

    public void setPoliticoAccionBoicot(Integer politicoAccionBoicot) {
        this.politicoAccionBoicot = politicoAccionBoicot;
    }

    public Integer getPoliticoAccionRecogerFirmas() {
        return politicoAccionRecogerFirmas;
    }

    public void setPoliticoAccionRecogerFirmas(Integer politicoAccionRecogerFirmas) {
        this.politicoAccionRecogerFirmas = politicoAccionRecogerFirmas;
    }

    public Integer getSocialCenaEspanoles() {
        return socialCenaEspanoles;
    }

    public void setSocialCenaEspanoles(Integer socialCenaEspanoles) {
        this.socialCenaEspanoles = socialCenaEspanoles;
    }

    public Integer getSocialContactosEspanoles() {
        return socialContactosEspanoles;
    }

    public void setSocialContactosEspanoles(Integer socialContactosEspanoles) {
        this.socialContactosEspanoles = socialContactosEspanoles;
    }

    public Integer getSocialParticipacionGrupoA() {
        return socialParticipacionGrupoA;
    }

    public void setSocialParticipacionGrupoA(Integer socialParticipacionGrupoA) {
        this.socialParticipacionGrupoA = socialParticipacionGrupoA;
    }

    public Integer getSocialParticipacionGrupoB() {
        return socialParticipacionGrupoB;
    }

    public void setSocialParticipacionGrupoB(Integer socialParticipacionGrupoB) {
        this.socialParticipacionGrupoB = socialParticipacionGrupoB;
    }

    public Integer getSocialParticipacionGrupoC() {
        return socialParticipacionGrupoC;
    }

    public void setSocialParticipacionGrupoC(Integer socialParticipacionGrupoC) {
        this.socialParticipacionGrupoC = socialParticipacionGrupoC;
    }

    public Integer getSocialParticipacionGrupoD() {
        return socialParticipacionGrupoD;
    }

    public void setSocialParticipacionGrupoD(Integer socialParticipacionGrupoD) {
        this.socialParticipacionGrupoD = socialParticipacionGrupoD;
    }

    public Integer getSocialParticipacionGrupoE() {
        return socialParticipacionGrupoE;
    }

    public void setSocialParticipacionGrupoE(Integer socialParticipacionGrupoE) {
        this.socialParticipacionGrupoE = socialParticipacionGrupoE;
    }

    public Integer getSocialMiembrosEspanolesGrupoA() {
        return socialMiembrosEspanolesGrupoA;
    }

    public void setSocialMiembrosEspanolesGrupoA(Integer socialMiembrosEspanolesGrupoA) {
        this.socialMiembrosEspanolesGrupoA = socialMiembrosEspanolesGrupoA;
    }

    public Integer getSocialMiembrosEspanolesGrupoB() {
        return socialMiembrosEspanolesGrupoB;
    }

    public void setSocialMiembrosEspanolesGrupoB(Integer socialMiembrosEspanolesGrupoB) {
        this.socialMiembrosEspanolesGrupoB = socialMiembrosEspanolesGrupoB;
    }

    public Integer getSocialMiembrosEspanolesGrupoC() {
        return socialMiembrosEspanolesGrupoC;
    }

    public void setSocialMiembrosEspanolesGrupoC(Integer socialMiembrosEspanolesGrupoC) {
        this.socialMiembrosEspanolesGrupoC = socialMiembrosEspanolesGrupoC;
    }

    public Integer getSocialMiembrosEspanolesGrupoD() {
        return socialMiembrosEspanolesGrupoD;
    }

    public void setSocialMiembrosEspanolesGrupoD(Integer socialMiembrosEspanolesGrupoD) {
        this.socialMiembrosEspanolesGrupoD = socialMiembrosEspanolesGrupoD;
    }

    public Integer getSocialMiembrosEspanolesGrupoE() {
        return socialMiembrosEspanolesGrupoE;
    }

    public void setSocialMiembrosEspanolesGrupoE(Integer socialMiembrosEspanolesGrupoE) {
        this.socialMiembrosEspanolesGrupoE = socialMiembrosEspanolesGrupoE;
    }

    public Integer getSocialFavoresEspanoles() {
        return socialFavoresEspanoles;
    }

    public void setSocialFavoresEspanoles(Integer socialFavoresEspanoles) {
        this.socialFavoresEspanoles = socialFavoresEspanoles;
    }

    public Integer getNavegacionalConsultaMedica() {
        return navegacionalConsultaMedica;
    }

    public void setNavegacionalConsultaMedica(Integer navegacionalConsultaMedica) {
        this.navegacionalConsultaMedica = navegacionalConsultaMedica;
    }

    public Integer getNavegacionalBuscarEmpleo() {
        return navegacionalBuscarEmpleo;
    }

    public void setNavegacionalBuscarEmpleo(Integer navegacionalBuscarEmpleo) {
        this.navegacionalBuscarEmpleo = navegacionalBuscarEmpleo;
    }

    public Integer getNavegacionalAyudaLegal() {
        return navegacionalAyudaLegal;
    }

    public void setNavegacionalAyudaLegal(Integer navegacionalAyudaLegal) {
        this.navegacionalAyudaLegal = navegacionalAyudaLegal;
    }

    public Integer getNavegacionalConduccionAlcohol() {
        return navegacionalConduccionAlcohol;
    }

    public void setNavegacionalConduccionAlcohol(Integer navegacionalConduccionAlcohol) {
        this.navegacionalConduccionAlcohol = navegacionalConduccionAlcohol;
    }

    public Integer getNavegacionalPagoImpuestos() {
        return navegacionalPagoImpuestos;
    }

    public void setNavegacionalPagoImpuestos(Integer navegacionalPagoImpuestos) {
        this.navegacionalPagoImpuestos = navegacionalPagoImpuestos;
    }

    public Integer getNavegacionalFormatoDireccion() {
        return navegacionalFormatoDireccion;
    }

    public void setNavegacionalFormatoDireccion(Integer navegacionalFormatoDireccion) {
        this.navegacionalFormatoDireccion = navegacionalFormatoDireccion;
    }

    public Integer getNavegacionalAyudaMedicaCronica() {
        return navegacionalAyudaMedicaCronica;
    }

    public void setNavegacionalAyudaMedicaCronica(Integer navegacionalAyudaMedicaCronica) {
        this.navegacionalAyudaMedicaCronica = navegacionalAyudaMedicaCronica;
    }
}