package io.github.uptalent.content.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillKudos {
    private String skill;
    private Long kudos;
}
