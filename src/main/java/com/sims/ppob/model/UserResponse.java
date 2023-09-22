package com.sims.ppob.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class UserResponse {

    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
