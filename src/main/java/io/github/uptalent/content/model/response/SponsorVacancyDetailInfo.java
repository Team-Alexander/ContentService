package io.github.uptalent.content.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SponsorVacancyDetailInfo extends VacancyDetailInfo{
    private List<SubmissionDetailInfo> submissions;
}
