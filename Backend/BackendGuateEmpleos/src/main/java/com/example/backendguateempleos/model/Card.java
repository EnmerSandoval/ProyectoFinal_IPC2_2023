package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Card {
    private int numberCard;
    private String nameChargeCard;
    private int cvv;
    private int cuiEmployer;
}
