package com.fabioqmarsiaj.condominiumresidents.repository;

import com.fabioqmarsiaj.condominiumresidents.model.Sindico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SindicoRepository extends MongoRepository<Sindico, String> {
}
