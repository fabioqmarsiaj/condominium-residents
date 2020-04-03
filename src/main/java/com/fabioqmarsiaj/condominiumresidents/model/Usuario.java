package com.fabioqmarsiaj.condominiumresidents.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "Usuario")
public class Usuario {

    @Id
    private String id;
    private String email;
    private List<Grupo> funcaoPorCondominio;

    public Usuario() {
    }

    public static final String TIPO = "USUARIO";

    @Override
    public String toString() {
        return email + ';' + funcaoPorCondominio;
    }
}
