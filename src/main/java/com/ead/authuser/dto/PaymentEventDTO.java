package com.ead.authuser.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentEventDTO {

    private UUID id;
    private UUID userId;
    private String paymentControl;
    private LocalDateTime paymentRequestDate;
    private LocalDateTime paymentCompletionDate;
    private LocalDateTime paymentExpirationDate;
    private String lastDigitsCreditCard;
    private BigDecimal valuePaid;
    private String paymentMessage;
    private boolean recurrence;

}
