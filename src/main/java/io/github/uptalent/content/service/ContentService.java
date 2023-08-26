package io.github.uptalent.content.service;

import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.model.request.ContentModify;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.service.visitor.ContentSaveVisitor;
import io.github.uptalent.content.service.visitor.ContentUpdateVisitor;
import io.github.uptalent.starter.model.common.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final AccountClient accountClient;
    private final ContentSaveVisitor contentSaveVisitor;
    private final ContentUpdateVisitor contentUpdateVisitor;


    public URI saveContent(ContentModify contentModify) {
        Author author = accountClient.getAuthor();
        return contentModify.accept(author, contentSaveVisitor);
    }

    public ContentDetailInfo updateContent(Long userId, String contentId, ContentModify contentModify) {
        return contentModify.accept(userId, contentId, contentUpdateVisitor);
    }
}
