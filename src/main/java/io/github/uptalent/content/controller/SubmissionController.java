package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.response.TalentSubmission;
import io.github.uptalent.content.service.SubmissionService;
import io.github.uptalent.starter.pagination.PageWithMetadata;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.github.uptalent.starter.util.Constants.USER_ID_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('TALENT')")
    public PageWithMetadata<TalentSubmission> getTalentSubmissions(
            @RequestHeader(USER_ID_KEY) Long userId,
            @Min(value = 0, message = "Page should be greater or equals 0")
            @RequestParam(defaultValue = "0") int page,
            @Positive(message = "Size should be positive")
            @RequestParam(defaultValue = "9") int size) {
        return submissionService.getTalentSubmissions(userId, page, size);
    }
}
