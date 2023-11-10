package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Commission {
    private int numberCommission;
    private int amount;
}
