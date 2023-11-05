package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryEmployer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="GuestMainServlet", urlPatterns="/guest/mainGuestServlet")
public class GuestMainServlet extends HttpServlet {

    Auxiliary<Job> auxiliary = new Auxiliary<>();
    QueryEmployer queryEmployer = new QueryEmployer();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Job> jobs = new ArrayList<>();
        jobs = queryEmployer.publishedWorks();
        for (int i = 0; i < jobs.size() ; i++) {
            System.out.println(jobs.get(i).toString());
        }
        auxiliary.sendList(resp, jobs);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Job> jobs = new ArrayList<>();
        jobs = queryEmployer.publishedWorks();
        for (int i = 0; i < jobs.size() ; i++) {
            System.out.println(jobs.get(i).toString());
        }
        auxiliary.sendList(resp, jobs);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
