package com.example.backendguateempleos.servlets;


import com.example.backendguateempleos.controller.CategoryService;
import com.example.backendguateempleos.controller.DataService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Category;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flag = req.getParameter("flag");
        if (flag != null && flag.equals("1")) {
            Data data = new Data();
            data.setNumberViews(dataService.getNumbersViews());
            data.setNumberEmployers(dataService.getNumbersEmployers());
            data.setNumberApplicants(dataService.getNumbersApplicants());
            auxiliary.send(resp, data);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else if (flag != null && flag.equals("2")) {
            var category = new Category();
            category.setDescription(req.getParameter("description"));
            category.setNameCategory(req.getParameter("nameCategory"));
            category.setNumberCategory(categoryService.maxNumberCategory() + 1);
            System.out.println("Andamos en insert");
            System.out.println(category.getDescription());
            if (categoryService.insertNewCategory(category)) {
                categoryAuxiliary.sendList(resp, categoryService.getAllCategories());
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        } else if (flag != null && flag.equals("3")) {
            categoryAuxiliary.sendList(resp, categoryService.getAllCategories());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else if (flag != null && flag.equals("4")) {
            var category = categoryAuxiliary.read(req, Category.class);
            if (categoryService.updateCategory(category)){
                categoryAuxiliary.sendList(resp, categoryService.getAllCategories());
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        }
    }
}
