package io.github.uptalent.content.service;

import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.exception.SubmissionNotFoundException;
import io.github.uptalent.content.mapper.TemplatedFeedbackMapper;
import io.github.uptalent.content.model.common.Feedback;
import io.github.uptalent.content.model.document.Submission;
import io.github.uptalent.content.model.document.TemplatedFeedback;
import io.github.uptalent.content.model.request.TemplatedFeedbackInfo;
import io.github.uptalent.content.repository.SubmissionRepository;
import io.github.uptalent.content.repository.TemplatedFeedbackRepository;
import io.github.uptalent.starter.model.common.Author;
import io.github.uptalent.starter.model.common.EventNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static io.github.uptalent.starter.model.enums.EventNotificationType.POST_FEEDBACK;
import static io.github.uptalent.starter.util.Constants.TALENT_SUFFIX;
import static io.github.uptalent.starter.util.Constants.VACANCIES_LINK;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final TemplatedFeedbackRepository templatedFeedbackRepository;
    private final AccountClient accountClient;
    private final SubmissionRepository submissionRepository;
    private final TemplatedFeedbackMapper templatedFeedbackMapper;
    private final EventNotificationProducerService eventNotificationProducerService;

    @Transactional
    public void createTemplatedFeedback(TemplatedFeedbackInfo templatedFeedbackInfo, Long sponsorId) {
        TemplatedFeedback templatedFeedback = templatedFeedbackMapper.toTemplatedFeedback(templatedFeedbackInfo);

        templatedFeedback.setSponsorId(sponsorId);
        templatedFeedbackRepository.save(templatedFeedback);
    }

    public List<TemplatedFeedbackInfo> findTemplatedFeedbackBySponsorId(Long sponsorId) {
        List<TemplatedFeedback> templatedFeedbacks = templatedFeedbackRepository.findAllBySponsorId(sponsorId);

        return  templatedFeedbackMapper.toTemplatedFeedbackInfoList(templatedFeedbacks);
    }

    @Transactional
    public void sendFeedbackToSubmission(Feedback feedback,
                                         Boolean saveAsTemplated,
                                         String submissionId,
                                         Long sponsorId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(SubmissionNotFoundException::new);

        if (!sponsorId.equals(submission.getVacancy().getAuthor().getId())) {
            throw new AccessDeniedException("You do not have permission to send feedback");
        }

        submission.setFeedback(feedback);
        submissionRepository.save(submission);
        sendEventNotification(submission.getAuthor().getId(), submission.getVacancy().getId());

        if (saveAsTemplated) {
            String title = generateTemplatedFeedbackTitle(sponsorId);
            TemplatedFeedbackInfo templatedFeedbackInfo = new TemplatedFeedbackInfo(title, feedback);
            createTemplatedFeedback(templatedFeedbackInfo, sponsorId);
        }
    }

    private String generateTemplatedFeedbackTitle(Long sponsorId) {
        return "Templated Title-" + UUID.randomUUID() + "-" + sponsorId.toString();
    }

    private void sendEventNotification(Long talentId, String vacancyId) {
        Author authorSponsor = accountClient.getAuthor();
        String to = talentId + TALENT_SUFFIX;
        String messageBody = POST_FEEDBACK.getMessageBody();
        String contentLink = VACANCIES_LINK + vacancyId;

        var eventNotificationMessage = EventNotificationMessage.builder()
                .from(authorSponsor)
                .to(to)
                .message(messageBody)
                .contentLink(contentLink)
                .build();
        eventNotificationProducerService.sendEventNotificationMsg(eventNotificationMessage);
    }
}
