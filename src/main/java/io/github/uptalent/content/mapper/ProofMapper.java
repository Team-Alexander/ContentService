package io.github.uptalent.content.mapper;

import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.response.ProofDetailInfo;
import io.github.uptalent.content.model.response.ProofGeneralInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProofMapper {
    Proof toProof(ProofModify proofModify);

    ProofDetailInfo toProofDetailInfo(Proof proof);

    List<ProofGeneralInfo> toProofGeneralInfoList(List<Proof> proofs);
}
