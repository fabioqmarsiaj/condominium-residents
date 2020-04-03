package com.fabioqmarsiaj.condominiumresidents.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "Usuario")
@Data
public class Usuario {

    @Id
    private String email;
    private Set<Grupo> funcaoPorCondominio = new HashSet<>();

    public Usuario() {
    }

    public static final String TIPO = "USUARIO";

    @Override
    public String toString() {
        return email + ';' + funcaoPorCondominio;
    }
}
