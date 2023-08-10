package io.github.uptalent.content.mapper;

import io.github.uptalent.content.model.document.Submission;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.SubmissionRequest;
import io.github.uptalent.content.model.request.VacancyModify;
import io.github.uptalent.content.model.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface VacancyMapper {
    Vacancy toVacancy(VacancyModify vacancyModify);
    List<VacancyGeneralInfo> toVacancyGeneralInfoList(List<Vacancy> vacancies);
    VacancyDetailInfo toVacancyDetailInfo(Vacancy vacancy);
    TalentVacancyDetailInfo toTalentVacancyDetailInfo(Vacancy vacancy);
    SponsorVacancyDetailInfo toSponsorVacancyDetailInfo(Vacancy vacancy);
    Submission toSubmission(SubmissionRequest submissionRequest);
    SubmissionGeneralInfo  toSubmissionGeneralInfo(Submission submission);
    SubmissionDetailInfo toSubmissionDetailInfo(Submission submission);
    ContentGeneralInfo  toContentGeneralInfo(Vacancy vacancy);
}
