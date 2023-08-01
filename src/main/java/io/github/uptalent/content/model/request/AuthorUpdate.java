package io.github.uptalent.content.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorUpdate {
    private String name;
    private String avatar;
}
