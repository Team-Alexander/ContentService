package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Proof;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProofRepository extends MongoRepository<Proof, String> {
    List<Proof> findAllByAuthorId(String authorId);

    void deleteAllByAuthorId(String authorId);
}
