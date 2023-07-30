package io.github.uptalent.content.model.document;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.enums.ContentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

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
}
