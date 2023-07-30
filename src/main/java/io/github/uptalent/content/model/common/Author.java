package io.github.uptalent.content.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Author {
    private String id;
    private String name;
    private String avatar;
}
