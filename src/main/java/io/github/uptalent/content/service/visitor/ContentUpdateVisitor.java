package io.github.uptalent.content.service.visitor;

import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.response.ContentDetailInfo;

public interface ContentUpdateVisitor {
    ContentDetailInfo updateContent(String userId, String contentId, ProofModify proofModify);
}
