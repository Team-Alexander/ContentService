package io.github.uptalent.content.model.response;

import io.github.uptalent.content.model.enums.ContentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ContentDetailInfo extends ContentGeneralInfo {
    private ContentStatus status;
    private String content;
}
