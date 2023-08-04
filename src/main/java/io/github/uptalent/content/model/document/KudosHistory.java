package io.github.uptalent.content.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("kudos_histories")
public class KudosHistory {
    @Id
    private String id;
    private String proofId;
    private long sponsorId;
    private long kudos;
    private LocalDateTime sent;
    private List<SkillKudos> kudosedSkills;
}
