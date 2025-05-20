package edu.mondragon.webengl.domain.user.model;

import java.util.Locale;

public class RecienLLegado extends User{
    String ciudad = null;
    String pais = null;
    Locale lang = null;

    public RecienLLegado(/*String username, String password, String firstName, String secondName, String email, Locale langu*/) {
        /*
        super.setUsername(username);
        super.setPassword(password);
        super.setFirstName(firstName);
        super.setSecondName(secondName);
        super.setEmail(email);
        super.setIdioma_principal(idioma_principal);
        */
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Locale getLang() {
        return lang;
    }

    public void setLang(Locale lang) {
        this.lang = lang;
    }

    
}
