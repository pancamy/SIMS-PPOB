package com.sims.ppob.model;

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
public class UserRegisterRequest {

    @NotEmpty(message = "Parameter email tidak boleh kosong")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Parameter email tidak sesuai format")
    private String email;

    @NotEmpty(message = "Parameter first name tidak boleh kosong")
    private String firstName;

    @NotEmpty(message = "Parameter last name tidak boleh kosong")
    private String lastName;

    @NotEmpty(message = "Parameter password tidak boleh kosong")
    @Size(min = 8, message = "Parameter password minimal 8 karakter")
    private String password;
}
