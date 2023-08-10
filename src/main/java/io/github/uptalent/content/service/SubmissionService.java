package io.github.uptalent.content.service;

import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.exception.DuplicateSubmissionException;
import io.github.uptalent.content.mapper.VacancyMapper;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Submission;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.SubmissionRequest;
import io.github.uptalent.content.model.response.SubmissionDetailInfo;
import io.github.uptalent.content.model.response.TalentSubmission;
import io.github.uptalent.content.repository.SubmissionRepository;
import io.github.uptalent.starter.pagination.PageWithMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.github.uptalent.content.model.enums.SubmissionStatus.SENT;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final VacancyMapper vacancyMapper;
    private final AccountClient accountClient;
    private final VacancyService vacancyService;

    public SubmissionDetailInfo sendSubmission(Long talentId, String vacancyId,
                                               SubmissionRequest submissionRequest) {
        Vacancy vacancy = vacancyService.getVacancyById(vacancyId);
        checkTalentSubmissionForVacancy(talentId, vacancy);

        Submission submission = vacancyMapper.toSubmission(submissionRequest);
        Author author = accountClient.getAuthor();

        submission.setVacancy(vacancy);
        submission.setAuthor(author);
        submission.setSent(LocalDateTime.now());
        submission.setStatus(SENT);

        submission = submissionRepository.save(submission);

        if (vacancy.getSubmissions() == null) {
            vacancy.setSubmissions(new ArrayList<>());
        }
        vacancy.getSubmissions().add(submission);
        vacancyService.update(vacancy);

        return vacancyMapper.toSubmissionDetailInfo(submission);
    }

    public PageWithMetadata<TalentSubmission> getTalentSubmissions(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Submission> submissionsPage = submissionRepository.findSubmissionsByAuthorId(pageRequest, userId);

        List<TalentSubmission> talentSubmissions = submissionsPage
                .stream()
                .map(submission -> TalentSubmission.builder()
                        .vacancy(vacancyMapper.toContentGeneralInfo(submission.getVacancy()))
                        .submission(vacancyMapper.toSubmissionGeneralInfo(submission))
                        .build())
                .toList();

        return new PageWithMetadata<>(talentSubmissions, submissionsPage.getTotalPages());
    }

    private void checkTalentSubmissionForVacancy(Long talentId, Vacancy vacancy) {
        if(submissionRepository.existsSubmissionByAuthorIdAndVacancyId(talentId, vacancy.getId()))
            throw new DuplicateSubmissionException();
    }
}
