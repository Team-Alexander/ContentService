package io.github.uptalent.content.exception;

public class SponsorNotFoundException extends RuntimeException {
    public SponsorNotFoundException() {
        super("Sponsor was not found");
    }
}
