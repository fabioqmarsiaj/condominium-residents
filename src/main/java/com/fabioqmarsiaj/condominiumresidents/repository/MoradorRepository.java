package com.fabioqmarsiaj.condominiumresidents.repository;

import com.fabioqmarsiaj.condominiumresidents.model.Morador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends MongoRepository<Morador, String> {
}
