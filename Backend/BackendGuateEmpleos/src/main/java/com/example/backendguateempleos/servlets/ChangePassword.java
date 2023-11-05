package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.AuxiliaryMethods;
import com.example.backendguateempleos.model.User;
import com.example.backendguateempleos.querys.QueryUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="ChangePassword", urlPatterns="/changePasswordServlet")
public class ChangePassword extends HttpServlet {

    private final Auxiliary<User> auxiliary = new Auxiliary<>();
    private final AuxiliaryMethods auxiliaryMethods = new AuxiliaryMethods();
    private final QueryUser queryUser = new QueryUser();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            var user = auxiliary.read(req, User.class);
        user.setPassword(auxiliaryMethods.encrypt(user.getPassword()));
        if(queryUser.updatePassword(user)){
            System.out.println("Se actualizo");
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
