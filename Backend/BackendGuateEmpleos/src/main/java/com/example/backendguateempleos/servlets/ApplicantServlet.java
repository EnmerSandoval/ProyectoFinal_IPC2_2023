package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.ApplicantService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.model.Job;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import java.io.IOException;
@WebServlet(name="ApplicantServlet", urlPatterns="/applicantServlet")
public class ApplicantServlet extends HttpServlet {

    private final Auxiliary<Category> auxiliary = new Auxiliary<>();
    private final Auxiliary<Job> jobAuxiliary = new Auxiliary<>();
    private final ApplicantService applicantService = new ApplicantService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flag = req.getParameter("flag");
        if ( flag != null && flag.equals("1")){
            List<Category> categoryList = auxiliary.readArray(req, Category.class);
            var numberCuiString = req.getParameter("numberCui");
            int numberCuiInt = Integer.parseInt(numberCuiString);
            if (!categoryList.isEmpty()){
                if (applicantService.insertOwnCategory(categoryList, numberCuiInt)){
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } if (flag != null && flag.equals("2")){
            var numberCuiString = req.getParameter("numberCui");
            if (numberCuiString != null){
                int numberCuiInt = Integer.parseInt(numberCuiString);
                jobAuxiliary.sendList(resp, applicantService.getRecomendedJobs(numberCuiInt));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } if (flag != null && flag.equals("3")){
            var numberCuiString = req.getParameter("numberCui");
            int numberCuiInt = Integer.parseInt(numberCuiString);
            var numberJobOffertString = req.getParameter("numberJobOffert");
            int numberJobOffertInt = Integer.parseInt(numberJobOffertString);
            var postulationReason = req.getParameter("postulationReason");
            if(applicantService.insertRequest(numberCuiInt, numberJobOffertInt, postulationReason)){
                resp.setStatus(HttpServletResponse.SC_OK);
            }

        }
    }
}
