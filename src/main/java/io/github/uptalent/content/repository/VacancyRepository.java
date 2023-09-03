package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.starter.model.enums.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends MongoRepository<Vacancy, String> {
    List<Vacancy> findAllByAuthorId(Long authorId);
    void deleteAllByAuthorId(Long authorId);

    Page<Vacancy> findAllByModerationStatus(PageRequest pageRequest, ModerationStatus moderationStatus);
}
