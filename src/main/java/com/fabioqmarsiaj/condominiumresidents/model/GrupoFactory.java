package com.fabioqmarsiaj.condominiumresidents.model;

import com.fabioqmarsiaj.condominiumresidents.exception.GrupoInvalidoException;
import com.fabioqmarsiaj.condominiumresidents.types.TiposGrupos;
import org.springframework.stereotype.Component;

@Component
public class GrupoFactory {

    private static class StaticHolder{ static final GrupoFactory INSTANCE = new GrupoFactory();}

    public static GrupoFactory getSingleton(){ return StaticHolder.INSTANCE; }

    public Grupo getGrupo(String tipoGrupo){

        Grupo novoGrupo;
        switch (TiposGrupos.valueOf(tipoGrupo.toUpperCase())){
            case MORADOR:
                novoGrupo =  new Morador();
                break;
            case SINDICO:
                novoGrupo =  new Sindico();
                break;
            default:
                throw new GrupoInvalidoException("Grupo inválido. Escolha entre MORADOR ou SINDICO");
        }
        return novoGrupo;
    }
}
