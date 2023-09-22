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

    @NotEmpty(message = "Email tidak boleh kosong")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email invalid")
    private String email;

    @NotEmpty(message = "First name tidak boleh kosong")
    private String firstName;

    @NotEmpty(message = "Last name tidak boleh kosong")
    private String lastName;

    @NotEmpty(message = "Password tidak boleh kosong")
    @Size(min = 8, message = "Password minimal 8 angka")
    private String password;
}
