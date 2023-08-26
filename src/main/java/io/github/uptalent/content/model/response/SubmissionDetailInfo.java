package io.github.uptalent.content.model.response;

import io.github.uptalent.starter.model.common.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubmissionDetailInfo extends SubmissionGeneralInfo {
    private String coverLetter;
    private Author author;
}
