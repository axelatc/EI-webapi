package com.axelatc.ei.models;

public enum Gender {
    FEMININ("F"),
    MASCULIN("M"),
    AUTRE("O");

    public final String label;

    Gender(final String label) {
        this.label = label;
    }
}
