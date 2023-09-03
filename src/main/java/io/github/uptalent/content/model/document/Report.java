package io.github.uptalent.content.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    private String id;
    @DBRef
    private Content content;
    private String message;
}
