package com.fabioqmarsiaj.condominiumresidents;

import com.fabioqmarsiaj.condominiumresidents.service.InputDadosService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CondominiumResidentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CondominiumResidentsApplication.class, args);

        InputDadosService inputDadosService = new InputDadosService();
        inputDadosService.run();
    }

}
