package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.exception.IllegalContentModifyingException;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.mapper.VacancyMapper;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.request.VacancyModify;
import io.github.uptalent.content.service.ProofService;
import io.github.uptalent.content.service.VacancyService;
import io.github.uptalent.starter.model.common.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

import static io.github.uptalent.content.model.enums.ContentStatus.PUBLISHED;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentSaveVisitorImpl implements ContentSaveVisitor {
    private final ProofService proofService;
    private final ProofMapper proofMapper;
    private final VacancyService vacancyService;
    private final VacancyMapper vacancyMapper;

    @Override
    public URI saveContent(Author author, ProofModify proofModify) {
        if (proofModify.getSkills().isEmpty() && proofModify.getStatus().equals(PUBLISHED.name())) {
            throw new IllegalContentModifyingException("Skills should be set for publishing");
        }

        Proof proof = proofMapper.toProof(proofModify);
        proof.setAuthor(author);
        proof.setSkills(proofMapper.convertSkills(proofModify.getSkills()));
        if (proofModify.getStatus().equals(PUBLISHED.name())) {
            proof.setPublished(LocalDateTime.now());
        }

        proof = proofService.save(proof);


        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proof.getId())
                .toUri();
    }

    @Override
    public URI saveContent(Author author, VacancyModify vacancyModify) {
        log.info("Vacancy: {}", vacancyModify);
        if (vacancyModify.getSkills().isEmpty() && vacancyModify.getStatus().equals(PUBLISHED.name())) {
            throw new IllegalContentModifyingException("Skills should be set for publishing");
        }

        Vacancy vacancy = vacancyMapper.toVacancy(vacancyModify);
        vacancy.setAuthor(author);
        if (vacancyModify.getStatus().equals(PUBLISHED.name())) {
            vacancy.setPublished(LocalDateTime.now());
        }

        vacancy = vacancyService.save(vacancy);

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vacancy.getId())
                .toUri();
    }
}
