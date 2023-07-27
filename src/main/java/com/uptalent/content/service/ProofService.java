package com.uptalent.content.service;

import com.uptalent.content.model.ContentStatus;
import com.uptalent.content.model.Proof;
import com.uptalent.content.repository.ProofRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProofService {
    private final ProofRepository proofRepository;

    public void save() {

        Proof proof = Proof.builder()
                .published(LocalDateTime.now())
                .content("Proof #1")
                .summary("Proof #1")
                .status(ContentStatus.PUBLISHED)
                .title("Proof #1")
                .iconNumber(1)
                .build();

        proofRepository.insert(proof);
    }

    public List<Proof> findAll() {

        return proofRepository.findAll();
    }
}
