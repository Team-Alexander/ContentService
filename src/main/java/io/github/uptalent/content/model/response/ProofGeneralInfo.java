package io.github.uptalent.content.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ProofGeneralInfo extends ContentGeneralInfo {
    private int iconNumber;
    private String summary;
    private long kudos;
}
