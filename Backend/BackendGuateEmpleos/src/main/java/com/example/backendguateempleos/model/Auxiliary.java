package com.example.backendguateempleos.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.protobuf.TextFormat;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
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

    public <T> T read(HttpServletRequest request, Class<T> classT) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String payload = buffer.toString();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.util.Date utilDate = sdf.parse(json.getAsString());
                        return new Date(utilDate.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .create();
        System.out.println(payload);
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
