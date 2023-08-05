package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.request.VacancyModify;

import java.net.URI;

public interface ContentSaveVisitor {
    URI saveContent( Author author, ProofModify proofModify);
    URI saveContent( Author author, VacancyModify vacancyModify);
}
