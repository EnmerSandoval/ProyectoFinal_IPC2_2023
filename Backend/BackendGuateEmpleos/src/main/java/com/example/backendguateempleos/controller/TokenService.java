package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.Token;
import com.example.backendguateempleos.model.User;
import com.example.backendguateempleos.querys.QueryTokens;

import java.util.Optional;

public class TokenService {

    QueryTokens queryTokens = new QueryTokens();

    public Optional<Token> tokenInsert(String token){
        return queryTokens.verifyTokenEntered(token);
    }
}
