package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.repository.ProofRepository;
import io.github.uptalent.content.service.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ContentSaveVisitorImpl implements ContentSaveVisitor {
    private final ProofService proofService;
    private final ProofMapper proofMapper;

    @Override
    public URI saveContent(Author author, ProofModify proofModify) {
        Proof proof = proofMapper.toProof(proofModify);
        proof.setAuthor(author);
        proof = proofService.save(proof);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proof.getId())
                .toUri();
    }
}
