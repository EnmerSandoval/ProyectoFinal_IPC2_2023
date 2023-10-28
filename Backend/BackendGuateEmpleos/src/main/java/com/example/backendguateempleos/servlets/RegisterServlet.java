package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.UserService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.User;
import com.example.backendguateempleos.querys.QueryUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="registerServlet", urlPatterns="/registerServlet")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final Auxiliary<User> auxiliary = new Auxiliary<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = auxiliary.read(req, User.class);
        var userRegister = userService.registerUserService(user);
            if (userRegister){
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }


    }
}
