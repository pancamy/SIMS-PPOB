package com.sims.ppob.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateRequest {

    @NotEmpty(message = "Parameter first name tidak boleh kosong")
    private String firstName;

    @NotEmpty(message = "Parameter last name tidak boleh kosong")
    private String lastName;
}
