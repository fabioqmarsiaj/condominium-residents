package com.fabioqmarsiaj.condominiumresidents.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@Document(collection = "Condominio")
public class Condominio {

    private String id;
    private List<Usuario> usuarios = new ArrayList<>();

    public Condominio() {
    }

    public Condominio(String id) {
        this.id = id;
    }

}
