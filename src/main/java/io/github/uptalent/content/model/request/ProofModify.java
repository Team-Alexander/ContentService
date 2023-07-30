package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.service.visitor.ContentSaveVisitor;
import io.github.uptalent.content.service.visitor.ContentUpdateVisitor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProofModify extends ContentModify {
    @NotBlank(message = "Summary should not be blank")
    @Size(max = 255, message = "Summary must be less than 255 characters")
    private String summary;

    @NotNull(message = "Icon number should not be null")
    @Min(value = 0, message = "Icon number shouldn't be less than 0")
    private Integer iconNumber;

    @Override
    public URI accept(Author author, ContentSaveVisitor visitor) {
        return visitor.saveContent(author, this);
    }

    @Override
    public ContentDetailInfo accept(String userId, String contentId, ContentUpdateVisitor visitor) {
        return visitor.updateContent(userId, contentId, this);
    }
}
