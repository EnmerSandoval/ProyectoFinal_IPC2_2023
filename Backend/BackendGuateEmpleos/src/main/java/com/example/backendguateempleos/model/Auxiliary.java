package com.example.backendguateempleos.model;

import com.google.gson.Gson;
import com.google.protobuf.TextFormat;
import jakarta.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Auxiliary<T> {
    private final Gson gson;

    public Auxiliary() {
        gson = new Gson();
    }

    public void send(HttpServletResponse response, T object) throws IOException {
        response.setContentType("application/json");
        String res = gson.toJson(object);
        var out = response.getWriter();
        out.print(res);
    }

    public void sendList(HttpServletResponse response, List<T> list) throws IOException {
        new Auxiliary<List<T>>().send(response, list);
    }

    public T read(HttpServletRequest request, Class<T> classT) throws IOException {
        var buffer = new StringBuilder();
        var reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) buffer.append(line);
        String payload = buffer.toString();
        return gson.fromJson(payload, classT);
    }

    public T getRequest(HttpServletRequest request, String parameterName, Class<T> classT) throws IOException {
        String jsonData = request.getParameter(parameterName);
        return gson.fromJson(jsonData, classT);
    }

    public String readString(HttpServletRequest request, Class<T> classT) throws IOException {
        var buffer = new StringBuilder();
        var reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) buffer.append(line);
        return buffer.toString();
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

    public java.sql.Date convertStringToSQLDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;

        try {
            utilDate = dateFormat.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
