package io.github.uptalent.content.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventNotificationMessage {
    private Author from;
    private String to;
    private String message;
    private String contentLink;
}
