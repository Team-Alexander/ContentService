package io.github.uptalent.content.model.document;

import io.github.uptalent.content.model.enums.ContentStatus;
import io.github.uptalent.starter.model.common.Author;
import io.github.uptalent.starter.model.enums.ModerationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime published;
    private ContentStatus status;
    private Author author;
    private ModerationStatus moderationStatus;
    @DBRef
    private List<Report> reports = new ArrayList<>();
}
