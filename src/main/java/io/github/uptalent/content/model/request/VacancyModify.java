package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.service.visitor.ContentSaveVisitor;
import io.github.uptalent.content.service.visitor.ContentUpdateVisitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VacancyModify extends ContentModify {
    @Override
    public URI accept(Author author, ContentSaveVisitor visitor) {
        return visitor.saveContent(author, this);
    }

    @Override
    public ContentDetailInfo accept(String userId, String contentId, ContentUpdateVisitor visitor) {
        return visitor.updateContent(userId, contentId, this);
    }
}
