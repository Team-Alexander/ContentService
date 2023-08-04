package io.github.uptalent.content.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Map;

@Data
public class PostKudosSkills {
    private Map<@NotBlank(message = "Skill should not be blank") String,
            @Positive(message = "Kudos should be positive") Long> kudosedSkills;
}
