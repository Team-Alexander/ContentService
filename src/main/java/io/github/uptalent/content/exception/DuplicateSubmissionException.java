package io.github.uptalent.content.exception;

public class DuplicateSubmissionException extends RuntimeException {
    public DuplicateSubmissionException() {
        super("You have already applied submission for this vacancy");
    }
}
