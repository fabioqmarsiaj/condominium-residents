package com.fabioqmarsiaj.condominiumresidents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Builder
@Component
@AllArgsConstructor
@Document(collection = "Morador")
@Data
public class Morador implements Grupo {

    @Id
    private String idCondominio;
    private static String NOME = "MORADOR";
    private HashMap<String, String> permissoesPorAcao = new HashMap<>();

    public Morador() {
    }

    public Morador(HashMap<String, String> permissoesPorAcao) {
        this.permissoesPorAcao = permissoesPorAcao;
    }

    @Override
    public String getIdCondominio() {
        return idCondominio;
    }

    @Override
    public void setIdCondominio(String idCondominio) {
        this.idCondominio = idCondominio;
    }

    @Override
    public HashMap<String, String> getPermissoesPorAcao() {
        return permissoesPorAcao;
    }

    @Override
    public void setPermissoesPorAcao(HashMap<String, String> permissoesPorAcao) {
        this.permissoesPorAcao = permissoesPorAcao;
    }

    @Override
    public String toString() {
        return permissoesPorAcao + "; " + idCondominio;
    }

}
