package com.axelatc.ei.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users", schema = "shapp")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;

    //TODO: add unique constraint
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

    //TODO: add unique constraint
    @NotBlank
    @Size(min = 3, max = 254)
    @Email(regexp = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")
    private String emailAddress;

    @NotNull
    @PastOrPresent
    private LocalDateTime creationDateTime;

    @NotNull
    private boolean isActive;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Measure> measures;
}
