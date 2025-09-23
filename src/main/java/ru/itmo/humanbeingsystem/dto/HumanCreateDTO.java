package ru.itmo.humanbeingsystem.dto;

import javax.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
public class HumanCreateDTO {
    @NotNull(message = "name must not be null")
    @NotEmpty(message = "fill name pls")
    private String name;

    // coords
    @NotNull(message = "x must not be null")
    private Integer x;
    @NotNull(message = "y must not be null")
    private Double y;

    private boolean realHero;
    private Boolean hasToothpick;

    // car
    private boolean carCool;

    private String mood;
    private long impactSpeed;

    @NotNull(message = "soundtrack must not be null")
    private String soundtrackName;

    @NotNull(message = "weapon type must not be null")
    private String weaponType;
}
