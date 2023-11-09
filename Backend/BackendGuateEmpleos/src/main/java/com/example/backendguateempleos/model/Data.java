package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Data {
    private int numberViews;
    private int numberEmployers;
    private int numberApplicants;
}
