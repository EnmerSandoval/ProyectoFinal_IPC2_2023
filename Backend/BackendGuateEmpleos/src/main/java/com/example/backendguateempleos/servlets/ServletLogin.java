package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.UserService;
import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="LoginServlet", urlPatterns ={"/loginServlet"})
public class ServletLogin extends HttpServlet {

    private final UserService userService = new UserService();
    private final Auxiliary<User> auxiliary = new Auxiliary<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = auxiliary.read(req, User.class);
        var passwordEncrypted = auxiliary.encrypt(user.getPassword());
        var userLogin = userService.loginService(user.getUsername(), passwordEncrypted);
        if (userLogin.isPresent()){
            System.out.println("SI ingreso");
            resp.setStatus(HttpServletResponse.SC_OK);
            auxiliary.send(resp, userLogin.get());
        }else {
            System.out.println("No ingreso");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }


}
