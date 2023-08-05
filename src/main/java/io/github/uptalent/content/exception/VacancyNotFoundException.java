package io.github.uptalent.content.exception;

public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException() {
        super("Vacancy was not found");
    }
}
