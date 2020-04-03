package com.fabioqmarsiaj.condominiumresidents.controller;

import com.fabioqmarsiaj.condominiumresidents.service.DadosUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class DadosUsuariosController {

    @Autowired
    private DadosUsuariosService service;

    @GetMapping(path = "/all" ,produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAllUsuarios(){
        return service.getAll().toString();
    }

    @PostMapping(path = "/usuarios")
    @ResponseBody
    public String getUsuarioPorEmail(@RequestParam String email){
        return service.findByEmail(email).toString();
    }
}
