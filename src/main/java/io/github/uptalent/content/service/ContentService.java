package io.github.uptalent.content.service;

import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.exception.ProofNotFoundException;
import io.github.uptalent.content.exception.VacancyNotFoundException;
import io.github.uptalent.content.model.document.Content;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.enums.ContentType;
import io.github.uptalent.content.model.request.ContentModify;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.repository.ProofRepository;
import io.github.uptalent.content.repository.VacancyRepository;
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
    private final ProofRepository proofRepository;
    private final VacancyRepository vacancyRepository;

    public URI saveContent(ContentModify contentModify) {
        Author author = accountClient.getAuthor();
        return contentModify.accept(author, contentSaveVisitor);
    }

    public ContentDetailInfo updateContent(Long userId, String contentId, ContentModify contentModify) {
        return contentModify.accept(userId, contentId, contentUpdateVisitor);
    }

    public Content getContentByIdAndType(String id, String contentType) {
        if (contentType.equals(ContentType.PROOF.name())) {
            return proofRepository.findById(id).orElseThrow(ProofNotFoundException::new);
        } else {
            return vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        }
    }

    public void save(Content content) {
        if (content instanceof Proof proof) {
            proofRepository.save(proof);
        } else if (content instanceof Vacancy vacancy) {
            vacancyRepository.save(vacancy);
        }
    }
}
