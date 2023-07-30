package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.request.ProofModify;

import java.net.URI;

public interface ContentSaveVisitor {
    URI saveContent( Author author, ProofModify proofModify);
}
