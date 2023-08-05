package io.github.uptalent.content.mapper;

import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.VacancyModify;
import io.github.uptalent.content.model.response.VacancyDetailInfo;
import io.github.uptalent.content.model.response.VacancyGeneralInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VacancyMapper {
    Vacancy toVacancy(VacancyModify vacancyModify);
    List<VacancyGeneralInfo> toVacancyGeneralInfoList(List<Vacancy> vacancies);
    VacancyDetailInfo toVacancyDetailInfo(Vacancy vacancy);
}
