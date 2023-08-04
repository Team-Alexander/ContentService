package io.github.uptalent.content.mapper;

import io.github.uptalent.content.model.document.Proof;
import io.github.uptalent.content.model.request.ProofModify;
import io.github.uptalent.content.model.response.ProofDetailInfo;
import io.github.uptalent.content.model.response.ProofGeneralInfo;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface ProofMapper {
    Proof toProof(ProofModify proofModify);

    ProofDetailInfo toProofDetailInfo(Proof proof);

    List<ProofGeneralInfo> toProofGeneralInfoList(List<Proof> proofs);

    default Map<String, Long> convertSkills(Set<String> skills) {
        Map<String, Long> kudosedSkills = new HashMap<>();
        skills.forEach(skill -> kudosedSkills.put(skill, 0L));
        return kudosedSkills;
    }
}
