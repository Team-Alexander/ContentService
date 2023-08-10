package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
    Page<Submission> findSubmissionsByAuthorId(Pageable pageable, Long talentId);
    Optional<Submission> findSubmissionByAuthorIdAndVacancyId(Long authorId, String vacancyId);
    boolean existsSubmissionByAuthorIdAndVacancyId(Long authorId, String vacancyId);
}
