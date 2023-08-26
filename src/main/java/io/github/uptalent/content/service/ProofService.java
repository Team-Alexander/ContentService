package io.github.uptalent.content.service;

import feign.FeignException;
import io.github.uptalent.content.client.AccountClient;
import io.github.uptalent.content.exception.IllegalPostingKudosException;
import io.github.uptalent.content.exception.ProofNotFoundException;
import io.github.uptalent.content.exception.SponsorNotFoundException;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.model.document.KudosHistory;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.common.SkillKudos;
import io.github.uptalent.content.model.request.PostKudosSkills;
import io.github.uptalent.content.model.response.PostKudosResult;
import io.github.uptalent.content.model.request.AuthorUpdate;
import io.github.uptalent.content.model.response.ProofDetailInfo;
import io.github.uptalent.content.model.response.ProofGeneralInfo;
import io.github.uptalent.content.repository.KudosHistoryRepository;
import io.github.uptalent.content.repository.ProofRepository;
import io.github.uptalent.starter.model.common.Author;
import io.github.uptalent.starter.model.common.EventNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.uptalent.content.model.enums.ContentStatus.PUBLISHED;
import static io.github.uptalent.content.util.ContentUtils.checkAuthorship;
import static io.github.uptalent.starter.model.enums.EventNotificationType.POST_KUDOS;
import static io.github.uptalent.starter.util.Constants.PROOFS_LINK;
import static io.github.uptalent.starter.util.Constants.TALENT_SUFFIX;

@Service
@RequiredArgsConstructor
public class ProofService {

    private final ProofRepository proofRepository;
    private final ProofMapper proofMapper;
    private final AccountClient accountClient;
    private final EventNotificationProducerService eventNotificationProducerService;
    private final KudosHistoryRepository kudosHistoryRepository;

    public List<ProofGeneralInfo> findAll() {
        return proofMapper.toProofGeneralInfoList(proofRepository.findAll());
    }

    public ProofDetailInfo getProofDetailInfo(String id) {
        return proofMapper.toProofDetailInfo(getProofById(id));
    }

    public void deleteProof(Long userId, String proofId) {
        Proof proof = getProofById(proofId);
        checkAuthorship(userId, proof);
        proofRepository.delete(proof);
    }

    public void updateProofsByAuthor(Long authorId, AuthorUpdate authorUpdate) {
        List<Proof> proofs = proofRepository.findAllByAuthorId(authorId);
        proofs.forEach(proof -> {
            Author author = proof.getAuthor();
            author.setName(authorUpdate.getName());
            author.setAvatar(authorUpdate.getAvatar());
        });
        proofRepository.saveAll(proofs);
    }

    @Transactional
    public PostKudosResult postKudos(PostKudosSkills request, String proofId, Long sponsorId) {
        Proof proof = getProofById(proofId);

        if (!proof.getStatus().equals(PUBLISHED))
            throw new AccessDeniedException("You cannot get access to proof.");

        List<SkillKudos> kudosedSkills = request.getKudosedSkills();
        Set<String> proofSkills = proof.getSkills().keySet();

        kudosedSkills.forEach(skillKudos -> {
            String skill = skillKudos.getSkill();
            if (proofSkills.contains(skill)) {
                proof.getSkills().merge(skill, skillKudos.getKudos(), Long::sum);
            } else {
                throw new IllegalPostingKudosException("The skill " +  skill + " does not exist in the proof.");
            }
        });

        long sumKudos = kudosedSkills.stream()
                .mapToLong(SkillKudos::getKudos)
                .sum();

        KudosHistory kudosHistory = KudosHistory.builder()
                .kudos(sumKudos)
                .proofId(proofId)
                .sponsorId(sponsorId)
                .sent(LocalDateTime.now())
                .kudosedSkills(kudosedSkills)
                .build();

        PostKudosResult result = calculateKudosTransaction(sumKudos, sponsorId, proof);

        kudosHistoryRepository.save(kudosHistory);
        sendEventNotification(request, proof);

        return result;
    }

    private PostKudosResult calculateKudosTransaction(Long sumKudos, Long sponsorId, Proof proof) {
        long currentSponsorBalance = calculateCurrentSponsorBalance(sumKudos);

        long additionalSumKudos = kudosHistoryRepository.sumKudosByProofIdAndSponsorId(proof.getId(), sponsorId).orElse(0L);
        long currentSumKudosBySponsor = sumKudos + additionalSumKudos;

        long currentProofKudos = proof.getKudos() + sumKudos;
        proof.setKudos(currentProofKudos);

        proofRepository.save(proof);

        return new PostKudosResult(currentProofKudos, currentSumKudosBySponsor, currentSponsorBalance);
    }

    private Long calculateCurrentSponsorBalance(Long sumKudos) {
        try {
            long sponsorBalance = accountClient.getSponsorBalance();
            long currentSponsorBalance = sponsorBalance - sumKudos;

            if (currentSponsorBalance < 0L)
                throw new IllegalPostingKudosException("You do not have enough balance for posting kudos.");

            accountClient.updateSponsorBalance(currentSponsorBalance);

            return currentSponsorBalance;
        } catch (FeignException.NotFound e) {
            throw new SponsorNotFoundException();
        }
    }

    private String generateSkillKudosEventMessage(PostKudosSkills request) {
        return request.getKudosedSkills().stream()
                .map(skillKudos -> skillKudos.getSkill() + ":" + skillKudos.getSkill())
                .collect(Collectors.joining(","));
    }

    private void sendEventNotification(PostKudosSkills request, Proof proof) {
        Author author = accountClient.getAuthor();
        String requestInfo = generateSkillKudosEventMessage(request);
        String to = proof.getAuthor().getId() + TALENT_SUFFIX;
        String messageBody = String.format(POST_KUDOS.getMessageBody(), requestInfo);
        String contentLink = PROOFS_LINK + proof.getId();

        var eventNotificationMessage = EventNotificationMessage.builder()
                .from(author)
                .to(to)
                .message(messageBody)
                .contentLink(contentLink)
                .build();
        eventNotificationProducerService.sendEventNotificationMsg(eventNotificationMessage);
    }

    public void deleteProofsByAuthor(Long authorId) {
        proofRepository.deleteAllByAuthorId(authorId);
    }

    public Proof getProofById(String id) {
        return proofRepository.findById(id)
                .orElseThrow(ProofNotFoundException::new);
    }

    public Proof save(Proof proof) {
        return proofRepository.insert(proof);
    }

    public Proof update(Proof proof) {
        return proofRepository.save(proof);
    }
}
