package io.github.uptalent.content.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventNotificationType {
    POST_KUDOS("Sponsor sent kudos to your proof. The sponsor posted: %s"),
    POST_SUBMISSION("Talent sent a submission to your vacancy. Please, check it."),
    POST_FEEDBACK("Author of vacancy sent feedback to your submission. Please, check it.");

    private final String messageBody;
}
