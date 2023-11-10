package com.example.backendguateempleos.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

public class AuxiliaryMethods {

    private final LocalDate currentDate = LocalDate.now();

    public LocalDate todayDate(){
        return  currentDate;
    }

    public LocalDate nextDay(){
        return currentDate.plusDays(1);
    }

    public String encrypt (String seasonedPassword){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        byte[] hash = md.digest(seasonedPassword.getBytes());
        StringBuilder buffer = new StringBuilder();
        for(byte b : hash) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public String generateSixCharacterToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder token = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            token.append(characters.charAt(randomIndex));
        }

        return token.toString();
    }
}
