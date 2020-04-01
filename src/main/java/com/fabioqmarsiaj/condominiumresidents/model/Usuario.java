package com.fabioqmarsiaj.condominiumresidents.model;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class Usuario {

    private String id;
    private String email;
    private HashMap<Grupo, String> funcaoPorCondominio;

    public Usuario() {
    }

    public Usuario(String id, String email, HashMap<Grupo, String> role) {
        this.id = id;
        this.email = email;
        this.funcaoPorCondominio = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<Grupo, String> getFuncaoPorCondominio() {
        return funcaoPorCondominio;
    }
}
