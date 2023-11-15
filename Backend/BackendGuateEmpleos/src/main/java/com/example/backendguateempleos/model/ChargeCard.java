package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChargeCard {
    private String nameChargeCard;
    private int numberCard;
    private int cvv;
}
