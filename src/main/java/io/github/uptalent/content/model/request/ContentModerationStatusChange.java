package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.enums.ContentType;
import io.github.uptalent.starter.util.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentModerationStatusChange {
    private String id;
    @EnumValue(enumClass = ContentType.class)
    private String contentType;
}
