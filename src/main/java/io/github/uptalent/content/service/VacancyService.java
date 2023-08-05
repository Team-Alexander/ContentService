package io.github.uptalent.content.service;

import io.github.uptalent.content.exception.VacancyNotFoundException;
import io.github.uptalent.content.mapper.VacancyMapper;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.response.VacancyDetailInfo;
import io.github.uptalent.content.model.response.VacancyGeneralInfo;
import io.github.uptalent.content.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.uptalent.content.util.ContentUtils.checkAuthorship;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public List<VacancyGeneralInfo> findAll() {
        return vacancyMapper.toVacancyGeneralInfoList(vacancyRepository.findAll());
    }

    public VacancyDetailInfo getVacancyDetailInfo(String id) {
        return vacancyMapper.toVacancyDetailInfo(getVacancyById(id));
    }

    public void deleteVacancy(String userId, String vacancyId) {
        Vacancy vacancy = getVacancyById(vacancyId);
        checkAuthorship(userId, vacancy);
        vacancyRepository.delete(vacancy);
    }

    public void updateVacanciesByAuthor(String authorId, AuthorUpdate authorUpdate) {
        List<Vacancy> vacancies = vacancyRepository.findAllByAuthorId(authorId);
        vacancies.forEach(proof -> {
            Author author = proof.getAuthor();
            author.setName(authorUpdate.getName());
            author.setAvatar(authorUpdate.getAvatar());
        });
        vacancyRepository.saveAll(vacancies);
    }

    public void deleteVacanciesByAuthor(String authorId) {
        vacancyRepository.deleteAllByAuthorId(authorId);
    }

    public Vacancy getVacancyById(String id) {
        return vacancyRepository.findById(id)
                .orElseThrow(VacancyNotFoundException::new);
    }

    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.insert(vacancy);
    }

    public Vacancy update(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }
}
