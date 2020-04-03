package com.fabioqmarsiaj.condominiumresidents.processor;

import com.fabioqmarsiaj.condominiumresidents.exception.LinhaErroException;
import com.fabioqmarsiaj.condominiumresidents.model.Grupo;
import com.fabioqmarsiaj.condominiumresidents.model.Morador;
import com.fabioqmarsiaj.condominiumresidents.model.Sindico;
import com.fabioqmarsiaj.condominiumresidents.model.Usuario;
import com.fabioqmarsiaj.condominiumresidents.types.TiposGrupos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Component
public class ProcessadorInput {

    private String homepath;
    private Set<String> allData = new TreeSet<>();
    private final String SEPARADOR = ";";
    private List<Morador> moradores = new ArrayList<>();
    private List<Sindico> sindicos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();


    private final BuilderProcessador builderProcessador;

    public ProcessadorInput(BuilderProcessador builderProcessador) {
        this.builderProcessador = builderProcessador;
    }


    public void processar() throws Exception {
        String linha;


        homepath = System.getProperty("user.home");
        File dir = new File(homepath + "/data/in/");

        createDirectoriesIfDoesntExist(dir);

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getAbsolutePath()))) {
                while ((linha = bufferedReader.readLine()) != null) {

                    verificarDadosEAdicionarNoAllData(linha);
                }
                checarMoradoresESindicos(moradores, sindicos, allData);
                checarUsuarios(usuarios, moradores, sindicos, allData);
                System.out.println(usuarios.toString());
                System.out.println(moradores.toString());
                System.out.println(sindicos.toString());
                System.out.println("--------------------------------");
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("File: " + file.getAbsolutePath() + " not found.");
            } catch (IOException e) {
                e.getMessage();
            }
        }

        System.out.println(allData);
    }

    private void checarUsuarios(List<Usuario> usuarios, List<Morador> moradores, List<Sindico> sindicos, Set<String> allData) {

        allData.forEach(line -> {
            if (line != null) {
                if (line.contains(Usuario.TIPO + SEPARADOR)) {
                    usuarios.add(builderProcessador.usuarioBuilder(line, moradores, sindicos));
                }
            }
            else {
                log.error("Error parsing line\n");
                throw new LinhaErroException("\nUm erro ocorreu ao parsear a linha.\n");
            }
        });

    }

    private void checarMoradoresESindicos(List<Morador> moradores, List<Sindico> sindicos, Set<String> allData) {

        allData.forEach(line -> {
            if (line != null) {
                if (line.contains(SEPARADOR + TiposGrupos.MORADOR.toString() + SEPARADOR)) {
                    moradores.add(builderProcessador.moradorBuilder(line));

                } else if (line.contains(SEPARADOR + TiposGrupos.SINDICO.toString() + SEPARADOR)) {
                    sindicos.add(builderProcessador.sindicoBuilder(line));
                }
            }else {
                log.error("Error parsing line\n");
                throw new LinhaErroException("\nUm erro ocorreu ao parsear a linha.\n");
            }
        });
    }


    private void createDirectoriesIfDoesntExist(File dir) throws IOException {
        if (!dir.exists()) {
            createDirectories(homepath);
        }
    }

    private void createDirectories(String homepath) throws IOException {
        Path pathToIn = Paths.get(homepath + "/data/in");
        Files.createDirectories(pathToIn);
    }

    private void verificarDadosEAdicionarNoAllData(String line) {
        if (line.contains(" ")) {
            allData.add(line.replace(" ", "").toUpperCase());
        } else {
            allData.add(line.toUpperCase());
        }
    }
}
