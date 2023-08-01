package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.model.response.ProofDetailInfo;
import io.github.uptalent.content.model.response.ProofGeneralInfo;
import io.github.uptalent.content.service.ContentService;
import io.github.uptalent.content.service.ProofService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.uptalent.starter.util.Constants.USER_ID_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content/proofs")
public class ProofController {
    private final ContentService contentService;
    private final ProofService proofService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProofGeneralInfo> findAll() {
        return proofService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ProofDetailInfo getProofDetailInfo(@PathVariable String id) {
        return proofService.getProofDetailInfo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('TALENT')")
    public URI saveProof(@Valid @RequestBody ProofModify proofModify) {
        return contentService.saveContent(proofModify);
    }

    @PatchMapping("/{contentId}")
    @PreAuthorize("hasAuthority('TALENT')")
    public ContentDetailInfo updateProof(@RequestHeader(USER_ID_KEY) String userId,
                                         @PathVariable String contentId,
                                         @Valid @RequestBody ProofModify proofModify) {
        return contentService.updateContent(userId, contentId, proofModify);
    }

    @DeleteMapping("/{contentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('TALENT')")
    public void deleteProof(@RequestHeader(USER_ID_KEY) String userId,
                            @PathVariable String contentId) {
        proofService.deleteProof(userId, contentId);
    }

    @PostMapping("/author")
    @PreAuthorize("hasAuthority('TALENT')")
    void updateProofsByAuthor(@RequestHeader(USER_ID_KEY) String authorId,
                              @RequestBody AuthorUpdate authorUpdate) {
        proofService.updateProofsByAuthor(authorId, authorUpdate);
    }

    @DeleteMapping("/author")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('TALENT')")
    public void deleteProofsByAuthor(@RequestHeader(USER_ID_KEY) String userId) {
        proofService.deleteProofsByAuthor(userId);
    }
}
