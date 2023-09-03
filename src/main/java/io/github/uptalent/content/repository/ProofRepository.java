package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.starter.model.enums.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProofRepository extends MongoRepository<Proof, String> {
    List<Proof> findAllByAuthorId(Long authorId);

    void deleteAllByAuthorId(Long authorId);

    Page<Proof> findAllByModerationStatus(PageRequest pageRequest, ModerationStatus moderationStatus);
}
