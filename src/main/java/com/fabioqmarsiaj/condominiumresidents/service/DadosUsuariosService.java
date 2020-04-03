package com.fabioqmarsiaj.condominiumresidents.service;

import com.fabioqmarsiaj.condominiumresidents.model.Usuario;
import com.fabioqmarsiaj.condominiumresidents.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadosUsuariosService{

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Usuario> getAll(){
        return usuariosRepository.findAll();
    }

    public Usuario findByEmail(String email) {
        return usuariosRepository.findUsuarioByEmail(email);
    }

}
