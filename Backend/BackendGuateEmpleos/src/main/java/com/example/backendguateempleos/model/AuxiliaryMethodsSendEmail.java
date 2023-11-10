package com.example.backendguateempleos.model;

public class AuxiliaryMethodsSendEmail {
    SendEmail sendEmail = new SendEmail();

    public void sendEmailToken(User user, String token){
        sendEmail.sendEmailJakarta(user.getEmail(), subjectToken(), contentToken(token));
    }

    public String contentToken(String token) {
        String link = "http://localhost:4200/register-token";
        String content = "GRACIAS POR USAR NUESTRO SERVICIO DE RESTABLECIMIENTO DE CONTRASENAS \n SU TOKEN ES: "
        + token + " POR FAVOR INGRESE AL SIGUIENTE LINK PARA PODER RESTABLECER SU CONTRASENA: \n" + link;
        return content;
    }

    public String subjectToken(){
        return "Recuperacion de Contrasena";
    }
}
