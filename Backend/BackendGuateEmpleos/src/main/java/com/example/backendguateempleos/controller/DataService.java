package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.Commission;
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

    public int getAmountCommission(){
        return queryData.amountCommission();
    }

    public boolean updateAmountCommission(Commission commission){
        return queryData.updateCommision(commission);
    }
}
