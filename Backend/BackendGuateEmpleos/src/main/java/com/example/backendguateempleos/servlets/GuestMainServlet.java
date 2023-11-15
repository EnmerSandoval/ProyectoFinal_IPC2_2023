package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Employer;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryEmployer;
import com.example.backendguateempleos.querys.QueryJob;
import com.example.backendguateempleos.querys.QueryVisits;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="GuestMainServlet", urlPatterns="/guest/mainGuestServlet")
public class GuestMainServlet extends HttpServlet {

    private final Auxiliary<Job> auxiliary = new Auxiliary<>();
    private final Auxiliary<Employer> employerAuxiliary = new Auxiliary<>();
    private final Auxiliary<Job> jobAuxiliary = new Auxiliary<>();
    private final QueryEmployer queryEmployer = new QueryEmployer();
    private final QueryVisits queryVisits = new QueryVisits();
    private final QueryJob queryJob = new QueryJob();

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
        } else if(flag != null && flag.equals("2")){
            Employer employer = new Employer();
            employer.setCui(Integer.parseInt(req.getParameter("cui")));
            employer = queryEmployer.getEmployerByCui(employer).get();
            if (employer != null){
                employerAuxiliary.send(resp, employer);
               resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else if(flag != null && flag.equals("3")){
            Job jobOffert = new Job();
            jobOffert.setNumberJobOffert(Integer.parseInt(req.getParameter("numberJobOffert")));
            jobOffert = queryJob.getJobByNumberOffert(jobOffert).get();
            if (jobOffert != null){
                jobAuxiliary.send(resp, jobOffert);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else if(flag != null && flag.equals("4")){
            int cui = Integer.parseInt(req.getParameter("employerCui"));
            List<Job> jobs = new ArrayList<>();
            jobs = queryJob.publishedWorksByCui(cui);
            if(jobs != null || jobs.isEmpty()){
                auxiliary.sendList(resp, jobs);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if( flag != null && flag.equals("5")){
            int numberJobOffert  = Integer.parseInt(req.getParameter("numberJobOffert"));
            if (queryJob.updateStateJob(numberJobOffert)){
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }else {
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }
}
