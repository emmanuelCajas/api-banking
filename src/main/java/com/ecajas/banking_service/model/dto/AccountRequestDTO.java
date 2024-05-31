package com.ecajas.banking_service.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    @NotBlank(message = "El numero de cuenta no puede estar vacio")
    @Size(min = 5, max = 20, message = "El numero debe tener entre 5 y 20 caracteres")
    @Pattern(regexp = "[0-9] +", message = "El nu mero de cuenta debe contener solo digitos")
    private String accountNumber;

    @NotNull(message = "El saldo no puede ser vacio")
    private BigDecimal balance;

    @NotBlank(message = "El nombre del titular no puede ser vacio")
    private String ownerName;

    @NotBlank(message = "El correo electronico d    el titular no puede ser vacio")
    @Email
    private String ownerEmail;

}
