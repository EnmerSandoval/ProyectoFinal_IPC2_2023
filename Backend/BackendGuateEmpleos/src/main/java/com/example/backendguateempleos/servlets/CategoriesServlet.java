package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.querys.QueryCategories;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="CategoriesServlet", urlPatterns="/categories/categoriesServlet")
public class CategoriesServlet extends HttpServlet {

    private final Auxiliary<Category> categoryAuxiliary = new Auxiliary<>();
    private final QueryCategories queryCategories = new QueryCategories();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag = req.getParameter("flag");
        if (flag != null && flag.equals("1")){
            categoryAuxiliary.sendList(resp, queryCategories.getAllCategories());

            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
