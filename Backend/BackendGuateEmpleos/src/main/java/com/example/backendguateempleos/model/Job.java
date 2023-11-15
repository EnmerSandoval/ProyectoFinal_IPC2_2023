package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Job {
    private int numberJobOffert;
    private String nameOfJobOffert;
    private String nameEmployer;
    private String description;
    private String location;
    private String details;
    private Date publicationDate;
    private Date applicationDeadline;
    private int state;
    private double salary;
    private int cuiEmployer;
    private String modality;
    private String category;
    private int numberCategorie;
    private int selectedUser;
}
