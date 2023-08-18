package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.common.Feedback;
import io.github.uptalent.content.model.request.TemplatedFeedbackInfo;
import io.github.uptalent.content.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.github.uptalent.starter.util.Constants.USER_ID_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PreAuthorize("hasAuthority('SPONSOR')")
    @PostMapping("/templated")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTemplatedFeedback(@RequestBody TemplatedFeedbackInfo templatedFeedbackInfo, 
                                        @RequestHeader(USER_ID_KEY) Long sponsorId) {
        feedbackService.createTemplatedFeedback(templatedFeedbackInfo, sponsorId);
    }

    @PreAuthorize("hasAuthority('SPONSOR')")
    @PostMapping("/templated")
    public List<TemplatedFeedbackInfo> findAllTemplatedFeedbacks(@RequestHeader(USER_ID_KEY) Long sponsorId) {
        return feedbackService.findTemplatedFeedbackBySponsorId(sponsorId);
    }

    @PreAuthorize("hasAuthority('SPONSOR')")
    @PostMapping("/{submissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendFeedback(@RequestBody Feedback feedback,
                             @RequestParam Boolean saveAsTemplated,
                             @PathVariable String submissionId,
                             @RequestHeader(USER_ID_KEY) Long sponsorId) {
        feedbackService.sendFeedbackToSubmission(feedback, saveAsTemplated, submissionId, sponsorId);
    }
}
