package com.example.backendguateempleos.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserCategories extends User{
    private int[] numberCategories;
}
