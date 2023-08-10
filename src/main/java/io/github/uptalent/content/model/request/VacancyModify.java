package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Content;
import io.github.uptalent.content.model.document.Vacancy;
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
    public ContentDetailInfo accept(Long userId, String contentId, ContentUpdateVisitor visitor) {
        return visitor.updateContent(userId, contentId, this);
    }

    @Override
    public void updateContentData(Content content) {
        Vacancy vacancy = (Vacancy) content;
        vacancy.setTitle(super.getTitle());
        vacancy.setContent(super.getContent());
        vacancy.setSkills(super.getSkills());
    }
}
