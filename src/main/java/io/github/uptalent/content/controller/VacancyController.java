package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.request.VacancyModify;
import io.github.uptalent.content.model.response.*;
import io.github.uptalent.content.service.ContentService;
import io.github.uptalent.content.service.VacancyService;
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
@RequestMapping("/api/v1/content/vacancies")
public class VacancyController {
    private final ContentService contentService;
    private final VacancyService vacancyService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<VacancyGeneralInfo> findAll() {
        return vacancyService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public VacancyDetailInfo getVacancyDetailInfo(@PathVariable String id) {
        return vacancyService.getVacancyDetailInfo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public URI saveVacancy(@Valid @RequestBody VacancyModify vacancyModify) {
        return contentService.saveContent(vacancyModify);
    }

    @PatchMapping("/{contentId}")
    @PreAuthorize("hasAuthority('SPONSOR')")
    public ContentDetailInfo updateVacancy(@RequestHeader(USER_ID_KEY) String userId,
                                           @PathVariable String contentId,
                                           @Valid @RequestBody VacancyModify vacancyModify) {
        return contentService.updateContent(userId, contentId, vacancyModify);
    }

    @DeleteMapping("/{vacancyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void deleteVacancy(@RequestHeader(USER_ID_KEY) String userId,
                            @PathVariable String vacancyId) {
        vacancyService.deleteVacancy(userId, vacancyId);
    }


    @PostMapping("/author")
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void updateProofsByAuthor(@RequestHeader(USER_ID_KEY) String authorId,
                                     @RequestBody AuthorUpdate authorUpdate) {
        vacancyService.updateVacanciesByAuthor(authorId, authorUpdate);
    }

    @DeleteMapping("/author")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void deleteProofsByAuthor(@RequestHeader(USER_ID_KEY) String userId) {
        vacancyService.deleteVacanciesByAuthor(userId);

    }
}
