package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.request.SubmissionRequest;
import io.github.uptalent.content.model.request.VacancyModify;
import io.github.uptalent.content.model.response.*;
import io.github.uptalent.content.service.ContentService;
import io.github.uptalent.content.service.SubmissionService;
import io.github.uptalent.content.service.VacancyService;
import io.github.uptalent.starter.security.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.uptalent.starter.util.Constants.USER_ID_KEY;
import static io.github.uptalent.starter.util.Constants.USER_ROLE_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content/vacancies")
public class VacancyController {
    private final ContentService contentService;
    private final VacancyService vacancyService;
    private final SubmissionService submissionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<VacancyGeneralInfo> findAll() {
        return vacancyService.findAll();
    }

    @GetMapping("/{vacancyId}")
    @PreAuthorize("isAuthenticated()")
    public VacancyDetailInfo getVacancyDetailInfo(@PathVariable String vacancyId,
                                                  @RequestHeader(USER_ID_KEY) Long userId,
                                                  @RequestHeader(USER_ROLE_KEY) Role userRole) {
        return vacancyService.getVacancyDetailInfo(vacancyId, userId, userRole);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public URI saveVacancy(@Valid @RequestBody VacancyModify vacancyModify) {
        return contentService.saveContent(vacancyModify);
    }

    @PatchMapping("/{contentId}")
    @PreAuthorize("hasAuthority('SPONSOR')")
    public ContentDetailInfo updateVacancy(@RequestHeader(USER_ID_KEY) Long userId,
                                           @PathVariable String contentId,
                                           @Valid @RequestBody VacancyModify vacancyModify) {
        return contentService.updateContent(userId, contentId, vacancyModify);
    }

    @DeleteMapping("/{vacancyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void deleteVacancy(@RequestHeader(USER_ID_KEY) Long userId,
                            @PathVariable String vacancyId) {
        vacancyService.deleteVacancy(userId, vacancyId);
    }


    @PatchMapping("/author")
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void updateVacanciesByAuthor(@RequestHeader(USER_ID_KEY) Long authorId,
                                     @RequestBody AuthorUpdate authorUpdate) {
        vacancyService.updateVacanciesByAuthor(authorId, authorUpdate);
    }

    @DeleteMapping("/author")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SPONSOR')")
    public void deleteVacanciesByAuthor(@RequestHeader(USER_ID_KEY) Long userId) {
        vacancyService.deleteVacanciesByAuthor(userId);
    }

    @PostMapping("/{vacancyId}/submission")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('TALENT')")
    public SubmissionDetailInfo sendSubmission(@Valid @RequestBody SubmissionRequest submissionRequest,
                                                @RequestHeader(USER_ID_KEY) Long talentId,
                                                @PathVariable String vacancyId) {
        return submissionService.sendSubmission(talentId, vacancyId, submissionRequest);
    }
}
