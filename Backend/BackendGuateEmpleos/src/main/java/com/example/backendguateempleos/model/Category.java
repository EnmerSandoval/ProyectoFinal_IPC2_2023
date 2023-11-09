package com.example.backendguateempleos.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Category {
    private int numberCategory;
    private String nameCategory;
    private String description;
}
