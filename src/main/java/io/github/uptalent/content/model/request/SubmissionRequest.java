package io.github.uptalent.content.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubmissionRequest {
    @Size(max = 1000, message = "Cover letter must be less than 1000 characters")
    private String coverLetter;
}
