package com.fabioqmarsiaj.condominiumresidents.model;

import com.fabioqmarsiaj.condominiumresidents.model.exception.GrupoInvalidoException;
import com.fabioqmarsiaj.condominiumresidents.model.types.TiposGrupos;

public class GroupFactory {

    public Grupo getGrupo(String tipoGrupo){

        switch (TiposGrupos.valueOf(tipoGrupo.toUpperCase())){

            case MORADOR:
                return new Morador();
            case SINDICO:
                return new Sindico();
            default:
                throw new GrupoInvalidoException("Grupo inválido. Escolha entre MORADOR ou SINDICO");
        }
    }
}
