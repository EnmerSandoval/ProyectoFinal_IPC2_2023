package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Card;
import com.example.backendguateempleos.model.Employer;
import com.example.backendguateempleos.querys.QueryEmployer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EmployerDescription", urlPatterns = "/employer/descriptionServlet")

public class EmployerServlet extends HttpServlet {

    private final Auxiliary<Employer> auxiliary = new Auxiliary<>();
    private final QueryEmployer queryEmployer = new QueryEmployer();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag = req.getParameter("flag");
        System.out.println(flag);
        if (flag != null && flag.equals("1")) {
            var employer = new Employer();
            var cui = Integer.parseInt(req.getParameter("cui"));
            var mission = req.getParameter("mission");
            var vision = req.getParameter("vision");
            employer.setMission(mission);
            employer.setCui(cui);
            employer.setVision(vision);
            if (employer != null) {
                if (queryEmployer.insertEmployerDescription(employer)) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        } else if (flag != null && flag.equals("2")){
            var card = new Card();
            var cuiEmployer = Integer.parseInt(req.getParameter("cuiEmployer"));
            var numberCard = Integer.parseInt(req.getParameter("numberCard"));
            var cvv = Integer.parseInt(req.getParameter("cvv"));
            var nameChargedCard = req.getParameter("nameChargedCard");
            card.setCuiEmployer(cuiEmployer);
            card.setNumberCard(numberCard);
            card.setCvv(cvv);
            card.setNameChargeCard(nameChargedCard);
            if (queryEmployer.insertCardEmployer(card)){
                System.out.println("YES");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }
    }
}
