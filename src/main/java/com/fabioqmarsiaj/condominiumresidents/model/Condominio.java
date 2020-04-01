package com.fabioqmarsiaj.condominiumresidents.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Condominio {

    private String id;
    private List<Usuario> usuarios = new ArrayList<>();

    public Condominio() {
    }

    public Condominio(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
