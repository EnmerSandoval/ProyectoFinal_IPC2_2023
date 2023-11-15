package com.example.backendguateempleos.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoadEmployer extends Employer{
    private List<Integer> phoneNumbers;
    private ChargeCard chargeCard;
}

