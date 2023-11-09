package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.querys.QueryData;

public class DataService {
    private final QueryData queryData = new QueryData();

    public int getNumbersViews(){
        return queryData.getViewsVisits();
    }

    public int getNumbersEmployers(){
        return queryData.numberEmployers();
    }

    public int getNumbersApplicants(){
        return queryData.numberApplicants();
    }
}
