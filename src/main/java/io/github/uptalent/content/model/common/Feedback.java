package io.github.uptalent.content.model.common;

import io.github.uptalent.content.model.enums.FeedbackStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Feedback {
    private String message;
    private FeedbackStatus status;
}
