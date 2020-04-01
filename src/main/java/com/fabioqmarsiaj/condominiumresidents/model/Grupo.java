package com.fabioqmarsiaj.condominiumresidents.model;

import com.fabioqmarsiaj.condominiumresidents.model.exception.Permissoes;
import com.fabioqmarsiaj.condominiumresidents.model.types.TiposGrupos;

import java.util.HashMap;

public interface Grupo {
    HashMap<TiposGrupos, Permissoes> getPermissoesPorFuncao();
    void setPermissoesPorFuncao(HashMap<TiposGrupos, Permissoes> permissoesPorFuncao);
}
