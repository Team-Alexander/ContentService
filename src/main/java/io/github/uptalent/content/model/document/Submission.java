package io.github.uptalent.content.model.document;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("submissions")
public class Submission {
    @Id
    private String id;

    private String coverLetter;

    private Author author;

    private SubmissionStatus status;

    private LocalDateTime sent;

    @DBRef
    private Vacancy vacancy;
}
