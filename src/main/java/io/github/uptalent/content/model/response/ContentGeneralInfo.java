package io.github.uptalent.content.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.uptalent.content.model.common.Author;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ContentGeneralInfo {
    private String id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime published;
    private Author author;
}
