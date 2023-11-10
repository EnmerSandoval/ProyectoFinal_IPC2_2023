package com.example.backendguateempleos.servlets;


import com.example.backendguateempleos.controller.CategoryService;
import com.example.backendguateempleos.controller.DataService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.model.Commission;
import com.example.backendguateempleos.model.Data;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdministratorData", urlPatterns = "/admin/administratorDataServlet")
public class AdministratorDataServlet extends HttpServlet {

    private final DataService dataService = new DataService();
    private final CategoryService categoryService = new CategoryService();
    private final Auxiliary<Data> auxiliary = new Auxiliary<>();
    private final Auxiliary<Category> categoryAuxiliary = new Auxiliary<>();

    private final Auxiliary<Commission> commissionAuxiliary = new Auxiliary<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        selectFlagAndResponse(req, resp);
    }

    private void selectFlagAndResponse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        var flag = request.getParameter("flag");
        if (flag != null && flag.equals("1")) {
            Data data = new Data();
            data.setNumberViews(dataService.getNumbersViews());
            data.setNumberEmployers(dataService.getNumbersEmployers());
            data.setNumberApplicants(dataService.getNumbersApplicants());
            auxiliary.send(response, data);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (flag != null && flag.equals("2")) {
            var category = new Category();
            category.setDescription(request.getParameter("description"));
            category.setNameCategory(request.getParameter("nameCategory"));
            category.setNumberCategory(categoryService.maxNumberCategory() + 1);
            if (categoryService.insertNewCategory(category)) {
                categoryAuxiliary.sendList(response, categoryService.getAllCategories());
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        } else if (flag != null && flag.equals("3")) {
            categoryAuxiliary.sendList(response, categoryService.getAllCategories());
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (flag != null && flag.equals("4")) {
            var category = new Category();
            category.setDescription(request.getParameter("description"));
            category.setNameCategory(request.getParameter("nameCategory"));
            category.setNumberCategory(Integer.parseInt(request.getParameter("numberCategory")));
            if (categoryService.updateCategory(category)){
                categoryAuxiliary.sendList(response, categoryService.getAllCategories());
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        } else if(flag != null && flag.equals("5")){
            var categoryNumber = new Category();
            categoryNumber.setNumberCategory(Integer.parseInt(request.getParameter("numberCategory")));
            var category = categoryService.getCategoryByNumber(categoryNumber);
            categoryAuxiliary.send(response, category.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else if(flag != null && flag.equals("6")){
            var commission = new Commission();
            commission.setNumberCommission(1);
            commission.setAmount(dataService.getAmountCommission());
            commissionAuxiliary.send(response, commission);
            response.setStatus(HttpServletResponse.SC_OK);
        } else if(flag != null && flag.equals("7")){
            var commission = new Commission();
            commission.setNumberCommission(Integer.parseInt(request.getParameter("numberCommission")));
            commission.setAmount(Integer.parseInt(request.getParameter("amount")));
            if (dataService.updateAmountCommission(commission)){
                commissionAuxiliary.send(response, commission);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }


}

