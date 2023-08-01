package io.github.uptalent.content.service;

import io.github.uptalent.content.exception.ProofNotFoundException;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.model.common.Author;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.response.ProofDetailInfo;
import io.github.uptalent.content.model.response.ProofGeneralInfo;
import io.github.uptalent.content.repository.ProofRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.uptalent.content.util.ContentUtils.checkAuthorship;

@Service
@RequiredArgsConstructor
public class ProofService {
    private final ProofRepository proofRepository;
    private final ProofMapper proofMapper;

    public List<ProofGeneralInfo> findAll() {
        return proofMapper.toProofGeneralInfoList(proofRepository.findAll());
    }

    public ProofDetailInfo getProofDetailInfo(String id) {
        return proofMapper.toProofDetailInfo(getProofById(id));
    }

    public void deleteProof(String userId, String proofId) {
        Proof proof = getProofById(proofId);
        checkAuthorship(userId, proof);
        proofRepository.delete(proof);
    }

    public void updateProofsByAuthor(String authorId, AuthorUpdate authorUpdate) {
        List<Proof> proofs = proofRepository.findAllByAuthorId(authorId);
        proofs.forEach(proof -> {
            Author author = proof.getAuthor();
            author.setName(authorUpdate.getName());
            author.setAvatar(authorUpdate.getAvatar());
        });
        proofRepository.saveAll(proofs);
    }

    public void deleteProofsByAuthor(String authorId) {
        proofRepository.deleteAllByAuthorId(authorId);
    }

    public Proof getProofById(String id) {
        return proofRepository.findById(id)
                .orElseThrow(ProofNotFoundException::new);
    }

    public Proof save(Proof proof) {
        return proofRepository.insert(proof);
    }

    public Proof update(Proof proof) {
        return proofRepository.save(proof);
    }
}
