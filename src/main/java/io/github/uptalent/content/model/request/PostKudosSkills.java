package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.common.SkillKudos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
public class PostKudosSkills {
    @NotNull(message = "Kudosed skills should not be blank")
    private List<@Valid SkillKudos> kudosedSkills;
}
