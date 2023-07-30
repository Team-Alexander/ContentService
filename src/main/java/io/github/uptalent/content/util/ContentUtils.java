package io.github.uptalent.content.util;

import io.github.uptalent.content.model.document.Content;
import org.springframework.security.access.AccessDeniedException;

import static io.github.uptalent.starter.util.Constants.ACCESS_DENIED_MESSAGE;

public final class ContentUtils {
    private ContentUtils() {}

    public static void checkAuthorship(String userId, Content content) {
        if(!content.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
        }
    }
}
