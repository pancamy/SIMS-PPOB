package com.sims.ppob.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceTopUpAmountRequest {

    @Min(value = 1, message = "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private Long topUpAmount;
}
