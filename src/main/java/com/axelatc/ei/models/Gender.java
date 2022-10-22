package com.axelatc.ei.models;

import lombok.Getter;

@Getter
public enum Gender {
    FEMALE("F"),
    MALE("M"),
    OTHER("O");

    private final String code;

    Gender(String code) {
        this.code = code;
    }
}
