package io.github.uptalent.content.exception;

public class SubmissionNotFoundException extends RuntimeException {
    public SubmissionNotFoundException() {
        super("Submission was not found");
    }
}
