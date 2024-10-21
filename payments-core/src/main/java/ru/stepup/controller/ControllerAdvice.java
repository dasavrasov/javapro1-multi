package ru.stepup.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.stepup.dto.ErrorResponse;
import ru.stepup.dto.IntegrationErrorDto;
import ru.stepup.dto.IntegrationException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleOtherException(Exception e) {
        ErrorResponse errorResponse =new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Ошибка платежного сервиса " + e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(IntegrationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIntegrationException(IntegrationException e) {
        IntegrationErrorDto errorDto = e.getErrorDto();
        String textMessage = errorDto.message();
        textMessage = textMessage + " : " + errorDto;
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), textMessage);
    }
}
