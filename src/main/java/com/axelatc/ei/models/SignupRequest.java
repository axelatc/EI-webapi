package com.axelatc.ei.models;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignupRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;

    @Column(unique = true)
    @NotBlank
    @Size(min = 1, max = 100)
    private String username;

    @NotBlank
    @Size(min = 6, max = 255)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,255})")
    private String password;

    @NotNull
    @PastOrPresent
    private LocalDate birthdate;

    @NotNull
    private Gender gender;

    @Column(unique = true)
    @NotBlank
    @Size(min = 3, max = 254)
    @Email(regexp = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")
    private String email;
}
