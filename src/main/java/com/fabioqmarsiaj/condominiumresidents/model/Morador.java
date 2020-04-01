package com.fabioqmarsiaj.condominiumresidents.model;

import com.fabioqmarsiaj.condominiumresidents.model.exception.Permissoes;
import com.fabioqmarsiaj.condominiumresidents.model.types.TiposGrupos;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Morador implements Grupo {

    private HashMap<TiposGrupos, Permissoes> permissoesPorFuncao;

    public Morador() {
    }

    public Morador(HashMap<TiposGrupos, Permissoes> permissaoPorFuncao) {
        this.permissoesPorFuncao = permissaoPorFuncao;
    }

    @Override
    public HashMap<TiposGrupos, Permissoes> getPermissoesPorFuncao() {
        return permissoesPorFuncao;
    }

    @Override
    public void setPermissoesPorFuncao(HashMap<TiposGrupos, Permissoes> permissoesPorFuncao) {
        this.permissoesPorFuncao = permissoesPorFuncao;
    }
}
