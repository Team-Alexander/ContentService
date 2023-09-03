package io.github.uptalent.content.service;

import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.mapper.VacancyMapper;
import io.github.uptalent.content.model.document.Content;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.document.Vacancy;
import io.github.uptalent.content.model.enums.ContentType;
import io.github.uptalent.content.model.request.ContentModerationStatusChange;
import io.github.uptalent.content.model.response.ContentReport;
import io.github.uptalent.starter.model.common.EmailMessageGeneralInfo;
import io.github.uptalent.starter.model.enums.ModerationStatus;
import io.github.uptalent.starter.pagination.PageWithMetadata;
import io.github.uptalent.starter.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static io.github.uptalent.starter.model.enums.ModerationStatus.ACTIVE;
import static io.github.uptalent.starter.model.enums.ModerationStatus.BLOCKED;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminContentService {
    private final ContentService contentService;
    private final ProofService proofService;
    private final VacancyService vacancyService;
    private final ProofMapper proofMapper;
    private final VacancyMapper vacancyMapper;
    private final AccountClient accountClient;
    private final EmailProducerService emailProducerService;

    public PageWithMetadata<ContentReport> getContentsWithModerationStatus(int page, int size, String status) {
        ModerationStatus moderationStatus = ModerationStatus.valueOf(status);
        int halfSize = size / 2;

        Page<Proof> proofs = proofService.
                getAllByModerationStatus(PageRequest.of(page, halfSize), moderationStatus);
        Page<Vacancy> vacancies = vacancyService.
                getAllByModerationStatus(PageRequest.of(page, size - halfSize), moderationStatus);

        List<ContentReport> talentReports = proofMapper.toContentReports(proofs.getContent());
        List<ContentReport> sponsorReports = vacancyMapper.toContentReports(vacancies.getContent());

        List<ContentReport> combined = new ArrayList<>(talentReports);
        combined.addAll(sponsorReports);

        long totalElements = proofs.getTotalElements() + vacancies.getTotalElements();
        int totalPages = totalElements % size == 0 ? (int) totalElements/size : (int) (totalElements/size) + 1;

        return new PageWithMetadata<>(combined, totalPages);
    }

    public void blockContent(ContentModerationStatusChange contentModerationStatusChange) {
        Content content = updateContentModerationStatus(contentModerationStatusChange, BLOCKED);
        String email = accountClient.getAccountEmailById(content.getAuthor().getId());
        String username = content.getAuthor().getName();

        var emailMessage = generateEmailMessage(username, email);
        emailProducerService.sendBlockedContentMsg(emailMessage);
    }

    public void unblockContent(ContentModerationStatusChange contentModerationStatusChange) {
        Content content = updateContentModerationStatus(contentModerationStatusChange, ACTIVE);
        String email = accountClient.getAccountEmailById(content.getAuthor().getId());
        String username = content.getAuthor().getName();

        var emailMessage = generateEmailMessage(username, email);
        emailProducerService.sendUnblockedContentMsg(emailMessage);
    }

    private Content updateContentModerationStatus(ContentModerationStatusChange contentModerationStatusChange,
                                        ModerationStatus status) {
        String id = contentModerationStatusChange.getId();
        String contentType = contentModerationStatusChange.getContentType();
        Content content = contentService.getContentByIdAndType(id, contentType);
        content.setModerationStatus(status);

        if(status == ACTIVE)
            content.getReports().clear();

        contentService.save(content);
        return content;
    }

    private EmailMessageGeneralInfo generateEmailMessage(String username, String email) {
        return EmailMessageGeneralInfo.builder()
                .username(username)
                .email(email)
                .build();
    }
}
