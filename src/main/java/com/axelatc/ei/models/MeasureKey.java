package com.axelatc.ei.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class MeasureKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "measurand_id")
    Long measurandId;

    @PastOrPresent
    @Column(name = "creation_datetime")
    LocalDateTime creationDateTime;
}
