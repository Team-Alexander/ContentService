package io.github.uptalent.content.model.document;

import io.github.uptalent.content.model.common.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("templated_feedbacks")
public class TemplatedFeedback {
    @Id
    private String id;
    private String title;
    private Feedback feedback;
    private Long sponsorId;
}
