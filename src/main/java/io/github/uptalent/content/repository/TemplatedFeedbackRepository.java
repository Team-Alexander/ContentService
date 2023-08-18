package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.TemplatedFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplatedFeedbackRepository extends MongoRepository<TemplatedFeedback, String> {
    List<TemplatedFeedback> findAllBySponsorId(Long sponsorId);
}
