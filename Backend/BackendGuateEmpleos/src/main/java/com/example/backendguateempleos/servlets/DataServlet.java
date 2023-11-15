package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.ReadData;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryEmployer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.http.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

@WebServlet(name="DataServlet", urlPatterns="/readDataServlet")
@MultipartConfig(location = "/tmp")
public class DataServlet extends HttpServlet {

    private static final String PART_NAME = "datafile";
    private final ReadData readData = new ReadData();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart(PART_NAME);
        String PATH = "/home/enmer/Documentos/files" + File.separatorChar + filePart.getSubmittedFileName();
        filePart.write(PATH);
        try (Reader reader = new FileReader(PATH)){
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            readData.readData(jsonObject, req);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | ParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
