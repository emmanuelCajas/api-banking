package com.ecajas.banking_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionReportDTO {
    private LocalDate date;
    private long transactionCount;
}
