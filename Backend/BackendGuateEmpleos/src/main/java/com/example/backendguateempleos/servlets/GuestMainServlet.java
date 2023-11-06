package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryEmployer;
import com.example.backendguateempleos.querys.QueryVisits;
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

    private final Auxiliary<Job> auxiliary = new Auxiliary<>();
    private final QueryEmployer queryEmployer = new QueryEmployer();
    private final QueryVisits queryVisits = new QueryVisits();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag = req.getParameter("flag");
        if (flag != null && flag.equals("1")){
            List<Job> jobs = new ArrayList<>();
            jobs = queryEmployer.publishedWorks();
            auxiliary.sendList(resp, jobs);
            if (!queryVisits.verifyVisits()){
                queryVisits.insertNumberVisits();
                resp.setStatus(HttpServletResponse.SC_OK);
            }  else {
                queryVisits.updateNumberVisits(queryVisits.numberVisits()+1);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }
}
