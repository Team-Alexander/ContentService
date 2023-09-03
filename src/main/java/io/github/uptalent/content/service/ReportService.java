package io.github.uptalent.content.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.uptalent.content.client.PerspectiveClient;
import io.github.uptalent.content.model.document.Content;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.document.Report;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.enums.ContentType;
import io.github.uptalent.content.repository.ReportRepository;
import io.github.uptalent.starter.model.common.Comment;
import io.github.uptalent.starter.model.common.EventNotificationMessage;
import io.github.uptalent.starter.model.common.TextItem;
import io.github.uptalent.starter.model.request.ReportRequest;
import io.github.uptalent.starter.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static io.github.uptalent.starter.model.enums.EventNotificationType.REPORT_ACCOUNT;
import static io.github.uptalent.starter.model.enums.ModerationStatus.ON_MODERATION;
import static io.github.uptalent.starter.security.Role.SPONSOR;
import static io.github.uptalent.starter.security.Role.TALENT;
import static io.github.uptalent.starter.util.Constants.*;
import static io.github.uptalent.starter.util.Constants.TOXICITY_THRESHOLD;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ProofService proofService;
    private final VacancyService vacancyService;
    private final ContentService contentService;
    private final EventNotificationProducerService eventNotificationProducerService;
    private final ObjectMapper objectMapper;
    private final PerspectiveClient perspectiveClient;

    @Value("${perspectiveapi.key}")
    private String perspectiveApiKey;

    @Value("${admin.id}")
    private Long adminId;

    public void reportProof(String id, ReportRequest reportRequest) {
        Proof proof = proofService.getProofById(id);
        reportContent(reportRequest, proof, PROOFS_LINK);
    }

    public void reportVacancy(String id, ReportRequest reportRequest) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        reportContent(reportRequest, vacancy, VACANCIES_LINK);
    }

    private void reportContent(ReportRequest reportRequest, Content content, String link) {
        content.setModerationStatus(ON_MODERATION);

        Report report = Report.builder()
                .message(reportRequest.getMessage())
                .content(content)
                .build();

        report = reportRepository.save(report);
        content.getReports().add(report);


        contentService.save(content);

        sendEventNotification(link, content.getAuthor().getId(), report.getMessage());
    }

    private void sendEventNotification(String link, Long reportedUserId, String reason) {
        String to = adminId + ADMIN_SUFFIX;
        String messageBody = REPORT_ACCOUNT.getMessageBody().formatted(reason);
        String contentLink = link + reportedUserId.toString();

        var eventNotificationMessage = EventNotificationMessage.builder()
                .to(to)
                .message(messageBody)
                .contentLink(contentLink)
                .build();
        eventNotificationProducerService.sendEventNotificationMsg(eventNotificationMessage);
    }

    public void checkToxicity(String id, String text, ContentType contentType) {
        double toxicityScore = getToxicityScore(text);
        if (toxicityScore > TOXICITY_THRESHOLD) {
            if (contentType == ContentType.PROOF)
                reportProof(id, new ReportRequest(text));
            else if (contentType == ContentType.VACANCY)
                reportVacancy(id, new ReportRequest(text));
        }
    }

    private double getToxicityScore(String text) {
        Comment comment =  Comment.builder()
                .languages(new String[]{"en"})
                .comment(new TextItem(text))
                .requestedAttributes(Map.of("TOXICITY", Collections.emptyMap()))
                .build();
        String response = perspectiveClient.analyzeText(perspectiveApiKey, comment);
        return parseToxicityScore(response);
    }

    @SneakyThrows
    private double parseToxicityScore(String jsonString) {
        JsonNode root = objectMapper.readTree(jsonString);
        return root.at("/attributeScores/TOXICITY/summaryScore/value").doubleValue();
    }
}
