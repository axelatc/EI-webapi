package com.axelatc.ei.models;

import com.axelatc.ei.utils.NullOrNotBlank;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Measure {
    @EmbeddedId
    private MeasureKey id;

    @ManyToOne
    @MapsId("measurandId")
    @JoinColumn(name = "measurand_id")
    private Measurand measurand;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;


    @PositiveOrZero
    @NotNull
    private BigDecimal value;

    @NullOrNotBlank
    @Size(min = 1, max = 5000)
    private String note;
}
