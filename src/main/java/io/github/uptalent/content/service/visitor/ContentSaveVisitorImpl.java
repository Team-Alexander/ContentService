package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.exception.IllegalContentModifyingException;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.service.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import static io.github.uptalent.content.model.enums.ContentStatus.PUBLISHED;

@Service
@RequiredArgsConstructor
public class ContentSaveVisitorImpl implements ContentSaveVisitor {
    private final ProofService proofService;
    private final ProofMapper proofMapper;

    @Override
    public URI saveContent(Author author, ProofModify proofModify) {
        if (proofModify.getSkills().isEmpty() && proofModify.getStatus().equals(PUBLISHED)) {
            throw new IllegalContentModifyingException("Skills should be set for publishing");
        }

        Proof proof = proofMapper.toProof(proofModify);
        proof.setAuthor(author);
        proof.setSkills(proofMapper.convertSkills(proofModify.getSkills()));
        proof = proofService.save(proof);


        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proof.getId())
                .toUri();
    }
}
