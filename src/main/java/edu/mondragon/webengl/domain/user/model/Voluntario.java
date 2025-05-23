package edu.mondragon.webengl.domain.user.model;

public class Voluntario extends User{
    String comunidad_autonoma=null;

    public Voluntario(/*String username, String password, String firstName, String secondName, String email, Locale lang*/) {
        /*
        super.setUsername(username);
        super.setPassword(password);
        super.setFirstName(firstName);
        super.setSecondName(secondName);
        super.setEmail(email);
        super.setLang(lang);
        */
    }

    public String getComunidad_autonoma() {
        return comunidad_autonoma;
    }

    public void setComunidad_autonoma(String comunidad_autonoma) {
        this.comunidad_autonoma = comunidad_autonoma;
    }

    
}
