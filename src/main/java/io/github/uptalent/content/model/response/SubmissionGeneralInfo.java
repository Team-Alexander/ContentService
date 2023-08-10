package io.github.uptalent.content.model.response;

import io.github.uptalent.content.model.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionGeneralInfo {
    private String id;
    private LocalDateTime sent;
    private SubmissionStatus status;
}
