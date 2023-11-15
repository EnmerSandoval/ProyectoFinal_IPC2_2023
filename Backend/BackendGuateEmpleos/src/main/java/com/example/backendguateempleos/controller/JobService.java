package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.Auxiliary;
import com.example.backendguateempleos.model.Job;
import com.example.backendguateempleos.querys.QueryJob;

import java.util.Optional;

public class JobService {

    private final Auxiliary<Job> auxiliary = new Auxiliary<Job>();
    private final QueryJob queryJob = new QueryJob();

    public Optional<Job> getJobByNumberJobOffer(int number){
        return queryJob.getJobForEdit(number);
    }

    public boolean updateJobOffert(Job job){
        return queryJob.updateJobOffert(job);
    }
    public boolean insertNewJobOffert(Job job){
        return queryJob.insertNewJobOffert(job);
    }

}
