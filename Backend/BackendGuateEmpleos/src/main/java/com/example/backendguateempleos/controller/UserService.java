package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.LoadEmployer;
import com.example.backendguateempleos.model.User;
import com.example.backendguateempleos.querys.QueryUser;

import java.util.List;
import java.util.Optional;

public class UserService {

    QueryUser queryUser;


    public UserService(){
        this.queryUser = new QueryUser();
    }

    public Optional<User> loginService(String username, String password){
        return queryUser.loginUser(username, password);
    }

    public boolean registerUserService(User user){
        return queryUser.registerUser(user);
    }

    public boolean verifyEmail(User user){
        return queryUser.verifyEmail(user);
    }
    public boolean registerPhoneNumberService(User user){
        return queryUser.insertPhoneNumber(user);
    }

    public List<User> employers(){
        return queryUser.usersForTypes(2);
    }

    public List<User> applicants(){
        return queryUser.usersForTypes(3);
    }

    public boolean insertEmployer(LoadEmployer loadEmployer){
        return queryUser.insertLoadEmployer(loadEmployer);
    }
}
