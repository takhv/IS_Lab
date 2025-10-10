package ru.itmo.humanbeingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Coordinates {
    @NotNull(message = "x must not be null")
    private Integer x;

    @NotNull(message = "y must not be null")
    private Double y;
}
