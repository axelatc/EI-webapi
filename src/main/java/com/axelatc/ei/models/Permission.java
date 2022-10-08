package com.axelatc.ei.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "permissions", schema = "shapp")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //TODO: add unique constraint
    @NotBlank
    @Size(min = 1, max = 100)
    private String label;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
