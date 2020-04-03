package com.fabioqmarsiaj.condominiumresidents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Builder
@Component
@AllArgsConstructor
@Document(collection = "Sindico")
public class Sindico implements Grupo {

    private String idCondominio;
    private static String NOME = "SINDICO";
    private HashMap<String, String> permissoesPorAcao;

    public Sindico() {
    }

    public Sindico(HashMap<String, String> permissoesPorAcao) {
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

    public void setPermissoesPorAcao(HashMap<String, String> permissoesPorAcao) {
        this.permissoesPorAcao = permissoesPorAcao;
    }

    @Override
    public String toString() {
        return  permissoesPorAcao + "; " + idCondominio;
    }
}
