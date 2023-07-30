package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.exception.IllegalContentModifyingException;
import io.github.uptalent.content.mapper.ProofMapper;
import io.github.uptalent.content.model.document.Content;
import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.enums.ContentStatus;
import io.github.uptalent.content.model.request.ContentModify;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.response.ContentDetailInfo;
import io.github.uptalent.content.service.ProofService;
import io.github.uptalent.content.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.uptalent.content.model.enums.ContentStatus.*;
import static io.github.uptalent.content.util.ContentUtils.checkAuthorship;

@Service
@RequiredArgsConstructor
public class ContentUpdateVisitorImpl implements ContentUpdateVisitor {
    private final ProofService proofService;
    private final ProofMapper proofMapper;

    private static final Map<Pair<ContentStatus, ContentStatus>, BiConsumer<Content, ContentModify>>
            STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put(new Pair<>(DRAFT, DRAFT), ContentUpdateVisitorImpl::updateContentData);
        STRATEGY_MAP.put(new Pair<>(DRAFT, PUBLISHED), ContentUpdateVisitorImpl::publishContent);
        STRATEGY_MAP.put(new Pair<>(PUBLISHED, HIDDEN), (content, contentModify) -> content.setStatus(HIDDEN));
        STRATEGY_MAP.put(new Pair<>(HIDDEN, PUBLISHED), (content, contentModify) -> content.setStatus(PUBLISHED));
    }

    @Override
    public ContentDetailInfo updateContent(String userId, String contentId, ProofModify proofModify) {
        Proof proof = proofService.getProofById(contentId);
        checkAuthorship(userId, proof);

        BiConsumer<Content, ContentModify> modifyingStrategy = selectModifyStrategy(proof, proofModify);
        modifyingStrategy.accept(proof, proofModify);

        proof = proofService.update(proof);

        return proofMapper.toProofDetailInfo(proof);
    }

    //TODO refactor
    private static void updateContentData(Content content, ContentModify contentModify) {
        if(contentModify instanceof ProofModify proofModify) {
            Proof proof = (Proof) content;
            proof.setTitle(proofModify.getTitle());
            proof.setSummary(proofModify.getSummary());
            proof.setContent(proofModify.getContent());
            proof.setIconNumber(proofModify.getIconNumber());
        }
    }

    private static void publishContent(Content content, ContentModify contentModify) {
        if (contentModify.getSkillIds() == null || contentModify.getSkillIds().isEmpty()) {
            throw new IllegalContentModifyingException("Skills should be set for publishing");
        }
        updateContentData(content, contentModify);
        content.setPublished(LocalDateTime.now());
        content.setStatus(PUBLISHED);
    }

    private BiConsumer<Content, ContentModify> selectModifyStrategy(Content content,
                                                                    ContentModify contentModify) {
        ContentStatus nextStatus = ContentStatus.valueOf(contentModify.getStatus());
        Pair<ContentStatus, ContentStatus> statusTransition = new Pair<>(content.getStatus(), nextStatus);

        BiConsumer<Content, ContentModify> modifyingStrategy = STRATEGY_MAP.get(statusTransition);

        if (modifyingStrategy == null) {
            throw new IllegalContentModifyingException("Illegal operation for modifying status ["
                    + content.getStatus() + " -> " + contentModify.getStatus() + "]");
        }
        return modifyingStrategy;
    }
}