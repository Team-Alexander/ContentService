package io.github.uptalent.content.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Author {
    private long id;
    private String name;
    private String avatar;
}
