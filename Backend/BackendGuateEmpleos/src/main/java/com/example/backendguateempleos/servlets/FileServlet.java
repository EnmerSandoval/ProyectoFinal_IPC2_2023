package com.example.backendguateempleos.servlets;


import com.example.backendguateempleos.controller.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet(name="FileServlet", urlPatterns="/file/readCv")
@MultipartConfig(location = "/tmp")
public class FileServlet extends HttpServlet {
    private final FileService fileService = new FileService();
    private static final String PART_NAME = "datafile";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cui = Integer.parseInt(req.getParameter("cui"));
        Part filePart = req.getPart(PART_NAME);

        if(fileService.filesService(cui, filePart)){
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
