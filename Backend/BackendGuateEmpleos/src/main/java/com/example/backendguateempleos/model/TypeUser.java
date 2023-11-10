package com.example.backendguateempleos.model;

public enum TypeUser {
    ADMINISTRATOR(1),
    EMPLOYER(2),
    APPLICANT(3);

    private int type;

    private TypeUser(int value) {
        this.type = value;
    }

    public int getValue() {
        return type;
    }
}
