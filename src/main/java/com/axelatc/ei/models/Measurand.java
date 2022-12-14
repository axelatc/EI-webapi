package com.axelatc.ei.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "measurands", schema = "shapp")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Measurand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank
    @Size(min = 1, max = 100)
    private String label;

    @OneToMany(mappedBy = "measurand")
    private Set<Measure> measures;
}
