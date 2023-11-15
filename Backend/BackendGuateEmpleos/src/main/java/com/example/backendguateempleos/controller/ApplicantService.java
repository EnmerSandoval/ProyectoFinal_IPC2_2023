package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryApplicant;

import java.util.List;

public class ApplicantService {
    private final QueryApplicant queryApplicant = new QueryApplicant();

    public boolean insertOwnCategory(List<Category> categoriesList, int cuiApplicant){
        return queryApplicant.insertCategoriesForApplicant(categoriesList, cuiApplicant);
    }

    public List<Job> getRecomendedJobs(int cui){
        return queryApplicant.recomendedJobs(cui);
    }

    public boolean insertRequest(int cui, int numberJobOffert, String postulationReason){
        return queryApplicant.insertRequest(cui, numberJobOffert, postulationReason);
    }

}
