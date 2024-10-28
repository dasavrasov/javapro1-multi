package ru.stepup.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long userId, String account, BigDecimal amount) {
}
