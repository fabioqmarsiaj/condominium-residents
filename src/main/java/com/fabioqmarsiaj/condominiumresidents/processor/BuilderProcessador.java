package com.fabioqmarsiaj.condominiumresidents.processor;

import com.fabioqmarsiaj.condominiumresidents.exception.GrupoInvalidoException;
import com.fabioqmarsiaj.condominiumresidents.model.*;
import com.fabioqmarsiaj.condominiumresidents.repository.MoradorRepository;
import com.fabioqmarsiaj.condominiumresidents.repository.SindicoRepository;
import com.fabioqmarsiaj.condominiumresidents.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BuilderProcessador {

    private List<Condominio> condominios = new ArrayList<>();
    private Condominio condominio;
    private List<String> ids = new ArrayList<>();

    private final UsuariosRepository usuariosRepository;
    private final MoradorRepository moradorRepository;
    private final SindicoRepository sindicoRepository;

    @Autowired
    public BuilderProcessador(UsuariosRepository usuariosRepository, MoradorRepository moradorRepository, SindicoRepository sindicoRepository) {
        this.usuariosRepository = usuariosRepository;
        this.moradorRepository = moradorRepository;
        this.sindicoRepository = sindicoRepository;
    }

    public Usuario usuarioBuilder(String linha, List<Grupo> moradores, List<Grupo> sindicos) {
        Set<Grupo> funcaoPorCondominio = new HashSet<>();
        ids.clear();

        String[] linhaSplitted = linha.split(";");

        String email = linhaSplitted[1];

        String[] hashSplit = linhaSplitted[2].split(",");

        Arrays.stream(hashSplit).forEach(string -> {
                    String stringFormatted = string.replaceAll("\\[", "").replaceAll("]", "")
                            .replaceAll("\\(", "").replaceAll("\\)", "");
                    if (stringFormatted.matches("\\d+")) {
                        ids.add(stringFormatted);
                    }
                }
        );

        String idCondominio = hashSplit[1].replaceAll("]", "")
                .replaceAll("\\)", "").replaceAll("\\)", "");


        condominio = new Condominio();

        addTipoDeGrupoPorId(linha, moradores, sindicos, funcaoPorCondominio);

        Usuario novoUsuario = Usuario.builder()
                .email(email)
                .funcaoPorCondominio(funcaoPorCondominio)
                .build();

        condominio.setId(idCondominio);
        condominio.getUsuarios().add(novoUsuario);
        condominios.add(condominio);

        usuariosRepository.save(novoUsuario);

        return novoUsuario;
    }

    private void addTipoDeGrupoPorId(String linha, List<Grupo> moradores, List<Grupo> sindicos, Set<Grupo> funcaoPorCondominio) {
        ids.forEach(id -> {
            if(linha.contains("MORADOR") && linha.contains("SINDICO")){
                moradores.forEach(morador -> {
                    if (morador.getIdCondominio().equals(id)) {
                        funcaoPorCondominio.add(morador);
                    }
                });

                sindicos.forEach(sindico -> {
                    if (sindico.getIdCondominio().equals(id)) {
                        funcaoPorCondominio.add(sindico);

                    }
                });
            }else{
                moradores.forEach(morador -> {
                    if (morador.getIdCondominio().equals(id)) {
                        funcaoPorCondominio.add(morador);
                    }
                });
            }
        });
    }

    public Grupo grupoBuilder(String linha){
        String[] linhaSplitted = linha.split(";");

        String tipoGrupo = linhaSplitted[1].toUpperCase();
        String idCondominio = linhaSplitted[2];

        String[] stringHash = linhaSplitted[3].split("\\),");

        String reservaEPermissao = stringHash[0].replaceAll("\\[", "").replaceAll("\\(", "");
        String entregasEPermissao = stringHash[1].replaceAll("\\(", "");
        String usuariosEPermissao = stringHash[2].replaceAll("\\(", "");

        String reservaEPermissaoHash[] = reservaEPermissao.split(",");

        String entregaEPermissaoHash[] = entregasEPermissao.split(",");

        String usuariosEPermissaoHash[] = usuariosEPermissao.split(",");

        HashMap<String, String> permissoesPorAcao = new HashMap<>();

        String valorUsuario = usuariosEPermissaoHash[1]
                .replaceAll("\\)", "")
                .replaceAll("]", "");

        permissoesPorAcao.put(reservaEPermissaoHash[0], reservaEPermissaoHash[1]);
        permissoesPorAcao.put(entregaEPermissaoHash[0], entregaEPermissaoHash[1]);
        permissoesPorAcao.put(usuariosEPermissaoHash[0], valorUsuario);

        return retornarOTipoDeGrupo(linha, tipoGrupo, idCondominio, permissoesPorAcao);
    }

    private Grupo retornarOTipoDeGrupo(String linha, String tipoGrupo, String idCondominio, HashMap<String, String> permissoesPorAcao) {
        if(tipoGrupo.equals("MORADOR")){
            Morador novoMorador = Morador.builder()
                    .idCondominio(idCondominio)
                    .permissoesPorAcao(permissoesPorAcao)
                    .build();

            moradorRepository.save(novoMorador);

            return novoMorador;

        }else if(tipoGrupo.equals("SINDICO")){
            Sindico novoSindico = Sindico.builder()
                    .idCondominio(idCondominio)
                    .permissoesPorAcao(permissoesPorAcao)
                    .build();

            sindicoRepository.save(novoSindico);

            return novoSindico;
        }else{
            throw new GrupoInvalidoException("Tipo de Grupo não encontrado na linha: " + linha);
        }
    }
}
