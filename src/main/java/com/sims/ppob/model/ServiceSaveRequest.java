package com.sims.ppob.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceSaveRequest {

    @NotEmpty(message = "Parameter service code tidak boleh kosong")
    private String serviceCode;

    @NotEmpty(message = "Parameter service name tidak boleh kosong")
    private String serviceName;

    @Min(value = 1, message = "Paramter service tariff hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private Long serviceTariff;
}
