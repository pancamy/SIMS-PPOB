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
public class BannerSaveRequest {

    @NotEmpty(message = "Parameter banner name tidak boleh kosong")
    private String bannerName;

    @NotEmpty(message = "Parameter description tidak boleh kosong")
    private String description;
}
