package com.fabioqmarsiaj.condominiumresidents.repository;

import com.fabioqmarsiaj.condominiumresidents.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends MongoRepository<Usuario, String> {

    Usuario findUsuarioByEmail(String email);

}
