package com.example.backendguateempleos.servlets;

import com.example.backendguateempleos.controller.UserService;
import com.example.backendguateempleos.model.*;
import com.example.backendguateempleos.querys.QueryTokens;
import com.example.backendguateempleos.querys.QueryUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ForgotPassword", urlPatterns="/forgotPassword")
public class ForgotPassword extends HttpServlet{

    private final QueryUser queryUser = new QueryUser();
    private final QueryTokens queryTokens = new QueryTokens();
    private final AuxiliaryMethods auxiliaryMethods = new AuxiliaryMethods();

    private final Auxiliary<User> auxiliary = new Auxiliary<User>();
    private final Auxiliary<Token> tokenAuxiliary = new Auxiliary<>();
    private final UserService userService = new UserService();
    private final AuxiliaryMethodsSendEmail auxiliaryMethodsSendEmail = new AuxiliaryMethodsSendEmail();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        String forgotPasswordParam = req.getParameter("forgotPassword");

        if (forgotPasswordParam != null && forgotPasswordParam.equals("1")) {
            user.setEmail(req.getParameter("email"));
            generateToken(req, resp, user);
        } else if(forgotPasswordParam != null && forgotPasswordParam.equals("2")){

        } else {
            resp.getWriter().write("El parámetro forgotPassword no es válido o está ausente");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LLEGAMOS AL POST");
        var token = tokenAuxiliary.read(req, Token.class);
        System.out.println(token.getTokenDate());
        System.out.println(token.getToken());
    }

    private void generateToken(HttpServletRequest req, HttpServletResponse resp, User user){
        if (userService.verifyEmail(user)){
            String token = auxiliaryMethods.generateSixCharacterToken();
            boolean tokenValid = true;
            user = queryUser.returnUserByEmail(user).get();
            do {
                if (!queryTokens.verifyExistTokenInTokens(token)){
                    if (!queryTokens.verifyExistTokenInTokensHistory(token)) {
                        if (queryTokens.verifyTokenWithState(user)){
                            queryTokens.updateToken(user, token);
                            //auxiliaryMethodsSendEmail.sendEmailToken(user, token);
                            resp.setStatus(HttpServletResponse.SC_OK);
                            tokenValid = false;
                        } else{
                            queryTokens.insertToken(user, token);
                            //  auxiliaryMethodsSendEmail.sendEmailToken(user, token);
                            resp.setStatus(HttpServletResponse.SC_OK);
                            tokenValid = false;
                        }
                    }else {
                        tokenValid = true;
                    }
                } else{
                    tokenValid = true;
                }
            } while (!tokenValid);
        } else {
            System.out.println("No existe el correo");
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    private void verifyToken(HttpServletRequest request, HttpServletResponse response, User user){

    }
}

