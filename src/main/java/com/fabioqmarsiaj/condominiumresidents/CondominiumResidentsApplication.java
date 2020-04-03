package com.fabioqmarsiaj.condominiumresidents;

import com.fabioqmarsiaj.condominiumresidents.processor.BuilderProcessador;
import com.fabioqmarsiaj.condominiumresidents.processor.ProcessadorInput;
import com.fabioqmarsiaj.condominiumresidents.repository.MoradorRepository;
import com.fabioqmarsiaj.condominiumresidents.repository.SindicoRepository;
import com.fabioqmarsiaj.condominiumresidents.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CondominiumResidentsApplication implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private SindicoRepository sindicoRepository;

    public static void main(String[] args){
        System.out.println("Analise de Dados Iniciada!");
        SpringApplication.run(CondominiumResidentsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BuilderProcessador builderProcessador = new BuilderProcessador(usuariosRepository, moradorRepository, sindicoRepository);

        ProcessadorInput processadorInput = new ProcessadorInput(builderProcessador);
        processadorInput.processar();
    }
}
