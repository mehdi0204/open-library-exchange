package fr.bookswap.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String username;

    @Email
    private String email;
}
