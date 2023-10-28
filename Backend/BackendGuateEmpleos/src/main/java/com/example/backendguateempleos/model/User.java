package com.example.backendguateempleos.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.ArrayList;


@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class User {
    private String address;
    private String birth;
    private int cui;
    private String email;
    private String name;
    private String password;
    private int phoneNumber;
    private int typeUser;
    private String username;
    private int codUser;


}
