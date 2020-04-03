package com.fabioqmarsiaj.condominiumresidents.model;

import java.util.HashMap;

public interface Grupo {

    String getIdCondominio();
    void setIdCondominio(String idCondominio);

    HashMap<String, String> getPermissoesPorAcao();
    void setPermissoesPorAcao(HashMap<String, String> permissoesPorAcao);
}
