package com.example.backendguateempleos.controller;


import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryEmployer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadData{
    private ObjectMapper objectMapper = new ObjectMapper();
    private InputStream fileContent;
    private BufferedReader bufferedReader;
    private JsonNode rootNode;
    private String json;
    public void readData(String fileContent, BufferedReader bufferedReader)  {
        this.bufferedReader = bufferedReader;
        this.json = fileContent;
        initJasonRootNode();
    }

    private void initJasonRootNode() {

        try {

            this.rootNode = objectMapper.readTree(json);
            System.out.println("exito ocn rootNode");
        } catch (IOException ex) {
            Logger.getLogger(FileMagnament.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void loadOffers()  {
        QueryEmployer insertOffer = new QueryEmployer();
        ArrayList<Job> jobs = new ArrayList<>();
        JsonNode offersNode = rootNode.get("ofertas");
        int state = 0;
        for (JsonNode offerNode : offersNode) {
            String fechaPublicacionString = offerNode.get("fechaPublicacion").asText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaPublicacion;
            try {
                fechaPublicacion = dateFormat.parse(fechaPublicacionString);
            } catch (ParseException e) {
                e.printStackTrace(); // o maneja de otra manera, por ejemplo, lanzando una excepción personalizada
            } catch (java.text.ParseException e) {
                throw new RuntimeException(e);
            }

            String fechaPublicacionString = offerNode.get("fechaPublicacion").asText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaPublicacion;
            try {
                fechaPublicacion = dateFormat.parse(fechaPublicacionString);
            } catch (ParseException e) {
                e.printStackTrace(); // o maneja de otra manera, por ejemplo, lanzando una excepción personalizada
                throw new RuntimeException(e);
            }

// Convierte la fecha a java.sql.Date
            java.sql.Date fechaSql = new java.sql.Date(fechaPublicacion.getTime());

            if(offerNode.get("estado").asText().equals("ACTIVA"){
                state = 1;
            }
            if(offerNode.get("estado").asText().equals("ENTREVISTA")){
                state = 2;
            }

            Job jobOffert = Job.builder()
                   .numberJobOffert(offerNode.get("codigo").asInt())
                    .nameOfJobOffert(offerNode.get("nombre").asText())
                    .cuiEmployer(offerNode.get("empresa").asInt())
                    .description(offerNode.get("descripcion").asText())
                    .numberJobOffert(offerNode.get("categoria").asInt())
                    .state(state)
                    .publicationDate()
                    .location(offerNode.get("ubicacion").asText())
                    .details(offerNode.get("details").asText())

            String publicationDate = offerNode.get("fechaPublicacion").asText();
            String expirationDate = offerNode.get("fechaLimite").asText();

            JsonNode applicationsNode = offerNode.get("solicitudes");
            List<Application> applications = new ArrayList<>();
            InsertApplication insertApplication = new InsertApplication();
            for (JsonNode applicationNode : applicationsNode) {
                int applicationCode = applicationNode.get("codigo").asInt();
                String seekerCode = applicationNode.get("usuario").asText();
                String message = applicationNode.get("mensaje").asText();
                Application application = new Application(applicationCode, new JobSeeker(seekerCode), offer, message, 0);
                applications.add(application);
            }

            for (Application application : applications) {
                System.out.println("insertando: " + application.toString());
                insertApplication.insertApplication(application);
            }

            JsonNode interviewNode = offerNode.get("entrevistas");
            List<Interview> interviews = new ArrayList<>();
            InsertInterview insertInw = new InsertInterview();
            for (JsonNode iNode : interviewNode) {
                int interviewCode = iNode.get("codigo").asInt();
                String seekerCode = iNode.get("usuario").asText();
                String date = iNode.get("fecha").asText();
                String hour = iNode.get("hora").asText();
                String direction2 = iNode.get("ubicacion").asText();
                String state2 = iNode.get("estado").asText();
                if (state.equals("PENDIENTE")){
                    intState= 0;
                } else if (state.equals("FINALIZADA")){
                    intState = 1;
                }
                String notes = iNode.get("notas").asText();
                int applicationCode = new SelectApplication().identifyApplication(new User(seekerCode), offer);
                Interview interview = new Interview(interviewCode, new Application(applicationCode), date, hour, intState, direction2, notes);
                interviews.add(interview);
            }

            for (Interview interview : interviews) {
                System.out.println("insertando: " + interview.toString());
                insertInw.insertInterview(interview);
            }

            offers.add(offer);
        }

        for (Offer offer : offers) {
            System.out.println("insertando: " + offer.toString());
            insertOffer.insertOffer(offer);
        }
    }


}
