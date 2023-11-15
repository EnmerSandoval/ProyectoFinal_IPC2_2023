package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.JobService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryJob;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="JobServlet", urlPatterns="/jobs/jobServlet")
public class JobServlet extends HttpServlet {

    private final Auxiliary<Job> auxiliary = new Auxiliary<Job>();
    private final QueryJob queryJob = new QueryJob();
    private final JobService jobService = new JobService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flag = req.getParameter("flag");
        if (flag != null && flag.equals("1")){
            var job = auxiliary.read(req, Job.class);
            if (job != null){
                if (jobService.insertNewJobOffert(job)){
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else if(flag != null && flag.equals("2")){
            var job = auxiliary.read(req, Job.class);
            if(job != null){
                if (jobService.updateJobOffert(job)){
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else if(flag != null && flag.equals("3")){
            var number = req.getParameter("number");
            if (number != null){
                int number2 = Integer.parseInt(number);
                var getJobOffert = jobService.getJobByNumberJobOffer(number2).get();
                auxiliary.send(resp, getJobOffert);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }

    }
}
