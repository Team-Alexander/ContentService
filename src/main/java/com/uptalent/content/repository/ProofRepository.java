package com.uptalent.content.repository;

import com.uptalent.content.model.Proof;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProofRepository extends MongoRepository<Proof, String> {
}
