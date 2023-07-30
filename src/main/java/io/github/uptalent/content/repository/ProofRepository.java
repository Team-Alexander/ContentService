package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Proof;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProofRepository extends MongoRepository<Proof, String> {
}
