package com.sims.ppob.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    @JsonIgnore
    private String id;

    @NotEmpty(message = "First name tidak boleh kosong")
    private String firstName;

    @NotEmpty(message = "Last name tidak boleh kosong")
    private String lastName;
}
