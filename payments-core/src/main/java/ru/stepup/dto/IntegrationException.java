package ru.stepup.dto;

public class IntegrationException extends RuntimeException {
    private IntegrationErrorDto errorDto;

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(IntegrationErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    public IntegrationErrorDto getErrorDto() {
        return errorDto;
    }
}
