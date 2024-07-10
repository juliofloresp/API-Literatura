package com.alura.literalura.LiterAlura.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Idiomas {
    private String en;

    public Idiomas(String en) {
        this.en = en;
    }

    public Idiomas() {

    }

    @Override
    public String toString() {
        return  "idioma ='" + en;
    }
}
