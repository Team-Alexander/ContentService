package io.github.uptalent.content.model.request;

import io.github.uptalent.content.model.common.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplatedFeedbackInfo {
    private String title;
    private Feedback feedback;
}
