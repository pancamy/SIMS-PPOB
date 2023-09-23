package com.sims.ppob.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionPaymentRequest {

    @NotEmpty(message = "Parameter service code tidak boleh kosong")
    private String serviceCode;
}
