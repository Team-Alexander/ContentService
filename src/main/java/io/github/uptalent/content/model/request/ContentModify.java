package io.github.uptalent.content.model.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.enums.ContentStatus;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.service.visitor.ContentSaveVisitor;
import io.github.uptalent.content.service.visitor.ContentUpdateVisitor;
import io.github.uptalent.starter.util.enums.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProofModify.class, name = "proofModify"),
        @JsonSubTypes.Type(value = VacancyModify.class, name = "vacancyModify")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ContentModify {
    @NotBlank(message = "Title should not be blank")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Content should not be blank")
    @Size(max = 5000, message = "Content must be less than 5000 characters")
    private String content;

    @EnumValue(enumClass = ContentStatus.class)
    private String status;

    @NotNull(message = "List of skills should not be null")
    @Size(max=30, message = "List of skills should be less than 30 items")
    private Set<String> skills;

    public abstract URI accept(Author author, ContentSaveVisitor visitor);

    public abstract ContentDetailInfo accept(String userId, String contentId, ContentUpdateVisitor visitor);
}
