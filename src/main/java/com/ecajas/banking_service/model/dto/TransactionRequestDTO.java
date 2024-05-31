package com.ecajas.banking_service.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionRequestDTO {

    private Long Id;
    @NotNull (message = "El numero de la cuenta de origen no puede estar vacio")
    private String sourceAccountNumber;

    @NotNull (message = "El numero de la cuenta de origen no puede estar vacio")
    private String targetAccountNumber;

    @NotNull (message = "La cantidad no puede estar vacia")
    @DecimalMin(value="0,01", message = "La cantidad debe ser mayor que cero")
    private BigDecimal amount;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;

}
