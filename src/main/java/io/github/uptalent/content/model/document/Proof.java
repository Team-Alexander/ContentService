package io.github.uptalent.content.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document("proofs")
@EqualsAndHashCode(callSuper = true)
public class Proof extends Content {
    private int iconNumber;
    private String summary;
    private long kudos;
    private Map<String, Long> skills;
}
