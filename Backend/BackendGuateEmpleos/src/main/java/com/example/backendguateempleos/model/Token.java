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
public class Token {
    private String token;
    private int cuiUser;
    private boolean state;
    private Date tokenDate;
    private Date tokenExpirationDate;
}
