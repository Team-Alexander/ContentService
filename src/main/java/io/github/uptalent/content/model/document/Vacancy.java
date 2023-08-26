package io.github.uptalent.content.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document("vacancies")
@EqualsAndHashCode(callSuper = true)
public class Vacancy extends Content {
    private Set<String> skills = new HashSet<>();
    @DBRef
    private List<Submission> submissions = new ArrayList<>();
}
