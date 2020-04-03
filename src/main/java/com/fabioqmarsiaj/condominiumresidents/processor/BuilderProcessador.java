package com.fabioqmarsiaj.condominiumresidents.processor;

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
    private Usuario novoUsuario;
    private List<Grupo> funcaoPorCondominio = new ArrayList<>();

    private final UsuariosRepository usuariosRepository;
    private final MoradorRepository moradorRepository;
    private final SindicoRepository sindicoRepository;

    @Autowired
    public BuilderProcessador(UsuariosRepository usuariosRepository, MoradorRepository moradorRepository, SindicoRepository sindicoRepository) {
        this.usuariosRepository = usuariosRepository;
        this.moradorRepository = moradorRepository;
        this.sindicoRepository = sindicoRepository;
    }

    public Usuario usuarioBuilder(String linha, List<Morador> moradores, List<Sindico> sindicos) {
        funcaoPorCondominio.clear();
        ids.clear();

        //Usuario;joao.costa@gmail.com;[(Morador,1),(Sindico,1),(Sindico,2)]
        String[] linhaSplitted = linha.split(";");

        String email = linhaSplitted[1];

        String[] hashSplit = linhaSplitted[2].split(",");
        //[(Morador
        // 1)
        // (Sindico
        // 1)
        // (Sindico
        // 2)]

        Arrays.stream(hashSplit).forEach(string -> {
                    String stringFormatted = string.replaceAll("\\[", "").replaceAll("]", "")
                            .replaceAll("\\(", "").replaceAll("\\)", "");
                    if (stringFormatted.matches("\\d+")) {
                        ids.add(stringFormatted);
                    }
                }
        );
        // ids 1 1 2

        String idCondominio = hashSplit[1].replaceAll("]", "")
                .replaceAll("\\)", "").replaceAll("\\)", "");


        condominio = new Condominio();


        ids.forEach(id -> {
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
        });

        novoUsuario = Usuario.builder()
                .email(email)
                .funcaoPorCondominio(funcaoPorCondominio)
                .build();

        condominio.setId(idCondominio);
        condominio.getUsuarios().add(novoUsuario);
        condominios.add(condominio);

        usuariosRepository.save(novoUsuario);

        return novoUsuario;
    }

    public Morador moradorBuilder(String codigo) {
        // Grupo;Morador;1;[(Reservas,Escrita),(Entregas,Nenhuma),(Usuarios,Leitura)]

        String[] linhaSplitted = codigo.split(";");
        //Grupo
        // Morador
        // 1
        // [(Reservas,Escrita),(Entregas,Nenhuma),(Usuarios,Leitura)]

        String tipoGrupo = linhaSplitted[1].toUpperCase();
        String idCondominio = linhaSplitted[2];

        String[] stringHash = linhaSplitted[3].split("\\),");
        // [(RESERVA,ESCRITA
        // (ENTREGAS,NENHUMA
        // (USUARIOS,LEITURA

        String reservaEPermissao = stringHash[0].replaceAll("\\[", "").replaceAll("\\(", "");
        String entregasEPermissao = stringHash[1].replaceAll("\\(", "");
        String usuariosEPermissao = stringHash[2].replaceAll("\\(", "");
        // RESERVA,ESCRITA
        // ENTREGAS,NENHUMA
        // USUARIOS,LEITURA

        String reservaEPermissaoHash[] = reservaEPermissao.split(",");
        // RESERVA
        // ESCRITA
        String entregaEPermissaoHash[] = entregasEPermissao.split(",");
        // ENTREGAS
        // NENHUMA
        String usuariosEPermissaoHash[] = usuariosEPermissao.split(",");
        // USUARIOS
        // LEITURA

        HashMap<String, String> permissoesPorAcao = new HashMap<>();

        String valorUsuario = usuariosEPermissaoHash[1]
                .replaceAll("\\)", "")
                .replaceAll("]", "");

        permissoesPorAcao.put(reservaEPermissaoHash[0], reservaEPermissaoHash[1]);
        permissoesPorAcao.put(entregaEPermissaoHash[0], entregaEPermissaoHash[1]);
        permissoesPorAcao.put(usuariosEPermissaoHash[0], valorUsuario);

        Morador newMorador = Morador.builder()
                .idCondominio(idCondominio)
                .permissoesPorAcao(permissoesPorAcao)
                .build();

        moradorRepository.save(newMorador);

        return newMorador;
    }

    public Sindico sindicoBuilder(String codigo) {
        String[] linhaSplitted = codigo.split(";");
        //Grupo
        // Morador
        // 1
        // [(Reservas,Escrita),(Entregas,Nenhuma),(Usuarios,Leitura)]

        String tipoGrupo = linhaSplitted[1].toUpperCase();
        String idCondominio = linhaSplitted[2];

        String[] stringHash = linhaSplitted[3].split("\\),");
        // [(RESERVA,ESCRITA
        // (ENTREGAS,NENHUMA
        // (USUARIOS,LEITURA

        String reservaEPermissao = stringHash[0].replaceAll("\\[", "").replaceAll("\\(", "");
        String entregasEPermissao = stringHash[1].replaceAll("\\(", "");
        String usuariosEPermissao = stringHash[2].replaceAll("\\(", "");
        // RESERVA,ESCRITA
        // ENTREGAS,NENHUMA
        // USUARIOS,LEITURA

        String reservaEPermissaoHash[] = reservaEPermissao.split(",");
        // RESERVA
        // ESCRITA
        String entregaEPermissaoHash[] = entregasEPermissao.split(",");
        // ENTREGAS
        // NENHUMA
        String usuariosEPermissaoHash[] = usuariosEPermissao.split(",");
        // USUARIOS
        // LEITURA

        HashMap<String, String> permissoesPorAcao = new HashMap<>();

        String valorUsuario = usuariosEPermissaoHash[1]
                .replaceAll("\\)", "")
                .replaceAll("]", "");

        permissoesPorAcao.put(reservaEPermissaoHash[0], reservaEPermissaoHash[1]);
        permissoesPorAcao.put(entregaEPermissaoHash[0], entregaEPermissaoHash[1]);
        permissoesPorAcao.put(usuariosEPermissaoHash[0], valorUsuario);

        Sindico newSindico = Sindico.builder()
                .idCondominio(idCondominio)
                .permissoesPorAcao(permissoesPorAcao)
                .build();

        sindicoRepository.save(newSindico);

        return newSindico;
    }
}
