package io.github.uptalent.content.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TalentSubmission {
    private ContentGeneralInfo vacancy;
    private SubmissionGeneralInfo submission;
}
