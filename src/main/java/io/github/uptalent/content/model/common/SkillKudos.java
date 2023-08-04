package io.github.uptalent.content.model.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillKudos {
    @NotBlank(message = "Skill should not be blank")
    private String skill;

    @Positive(message = "Kudos should be positive")
    private Long kudos;
}
