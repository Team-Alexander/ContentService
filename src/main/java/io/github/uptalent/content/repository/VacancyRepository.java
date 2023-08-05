package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends MongoRepository<Vacancy, String> {
    List<Vacancy> findAllByAuthorId(String authorId);
    void deleteAllByAuthorId(String authorId);
}
