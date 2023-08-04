package io.github.uptalent.content.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostKudosResult {
    private long currentCountKudos;
    private long currentSumKudosBySponsor;
    private long currentSponsorBalance;
}
