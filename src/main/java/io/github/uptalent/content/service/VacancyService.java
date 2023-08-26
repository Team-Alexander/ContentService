package io.github.uptalent.content.service;

import io.github.uptalent.content.exception.VacancyNotFoundException;
import io.github.uptalent.content.mapper.VacancyMapper;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.response.*;
import io.github.uptalent.content.repository.SubmissionRepository;
import io.github.uptalent.content.repository.VacancyRepository;
import io.github.uptalent.starter.model.common.Author;
import io.github.uptalent.starter.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.uptalent.content.util.ContentUtils.checkAuthorship;
import static io.github.uptalent.starter.security.Role.TALENT;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final SubmissionRepository submissionRepository;

    public List<VacancyGeneralInfo> findAll() {
        return vacancyMapper.toVacancyGeneralInfoList(vacancyRepository.findAll());
    }

    public VacancyDetailInfo getVacancyDetailInfo(String vacancyId, Long userId, Role userRole) {
        Vacancy vacancy = getVacancyById(vacancyId);
        if(userRole == TALENT) {
            var talentVacancyDetailInfo = vacancyMapper.toTalentVacancyDetailInfo(vacancy);
            var talentSubmission = submissionRepository.findSubmissionByAuthorIdAndVacancyId(userId, vacancyId);
            talentSubmission.ifPresent(submission -> talentVacancyDetailInfo
                    .setMySubmission(vacancyMapper.toSubmissionDetailInfo(submission))
            );
            return talentVacancyDetailInfo;
        }
        else if(vacancy.getAuthor().getId() == userId) {
            return vacancyMapper.toSponsorVacancyDetailInfo(vacancy);
        } else {
            return vacancyMapper.toVacancyDetailInfo(vacancy);
        }
    }

    public void deleteVacancy(Long userId, String vacancyId) {
        Vacancy vacancy = getVacancyById(vacancyId);
        checkAuthorship(userId, vacancy);
        vacancyRepository.delete(vacancy);
    }

    public void updateVacanciesByAuthor(Long authorId, AuthorUpdate authorUpdate) {
        List<Vacancy> vacancies = vacancyRepository.findAllByAuthorId(authorId);
        vacancies.forEach(proof -> {
            Author author = proof.getAuthor();
            author.setName(authorUpdate.getName());
            author.setAvatar(authorUpdate.getAvatar());
        });
        vacancyRepository.saveAll(vacancies);
    }

    public void deleteVacanciesByAuthor(Long authorId) {
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
