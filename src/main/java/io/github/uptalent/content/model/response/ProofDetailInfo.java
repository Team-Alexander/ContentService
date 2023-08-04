package io.github.uptalent.content.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ProofDetailInfo extends ContentDetailInfo {
    private int iconNumber;
    private String summary;
    private Map<String, Long> skills;
    private long kudos;
}