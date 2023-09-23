package com.sims.ppob.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BannerResponse {

    private String id;

    private String bannerName;

    private String bannerImage;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
