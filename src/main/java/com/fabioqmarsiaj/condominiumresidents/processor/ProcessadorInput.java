package com.fabioqmarsiaj.condominiumresidents.processor;

import com.fabioqmarsiaj.condominiumresidents.exception.GrupoInvalidoException;
import com.fabioqmarsiaj.condominiumresidents.exception.LinhaErroException;
import com.fabioqmarsiaj.condominiumresidents.model.Grupo;
import com.fabioqmarsiaj.condominiumresidents.model.Morador;
import com.fabioqmarsiaj.condominiumresidents.model.Sindico;
import com.fabioqmarsiaj.condominiumresidents.model.Usuario;
import com.fabioqmarsiaj.condominiumresidents.types.TiposGrupos;
import lombok.extern.slf4j.Slf4j;
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
    private List<Grupo> moradores = new ArrayList<>();
    private List<Grupo> sindicos = new ArrayList<>();
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

            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("File: " + file.getAbsolutePath() + " not found.");
            } catch (IOException e) {
                e.getMessage();
            }
        }

        System.out.println(allData);
    }

    private void checarUsuarios(List<Usuario> usuarios, List<Grupo> moradores, List<Grupo> sindicos, Set<String> allData) {

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

    private void checarMoradoresESindicos(List<Grupo> moradores, List<Grupo> sindicos, Set<String> allData) {

        allData.forEach(line -> {
            if (line != null) {
                if ((line.contains(SEPARADOR + TiposGrupos.MORADOR.toString() + SEPARADOR)) || (line.contains(SEPARADOR + TiposGrupos.SINDICO.toString() + SEPARADOR))) {
                    Grupo newGrupo = builderProcessador.grupoBuilder(line);

                    if (newGrupo instanceof Morador) {
                        moradores.add(newGrupo);
                    }else if(newGrupo instanceof Sindico){
                        sindicos.add(newGrupo);
                    }else{
                        throw new GrupoInvalidoException("Instância não encontrada...");
                    }
                }
            }else {
                log.error("Erro na linha\n");
                throw new LinhaErroException("\nA linha é nula.\n");
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
