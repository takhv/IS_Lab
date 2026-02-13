package ru.itmo.humanbeingsystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
public class HumanCreateDTO {
  @NotNull @NotEmpty private String name;

  @NotNull private Integer x;
  @NotNull private Double y;

  private boolean realHero;
  private boolean hasToothpick;

  private Boolean carCool;
  private String carType;

  private String mood;
  private long impactSpeed;

  @NotNull private String soundtrackName;

  @NotNull private String weaponType;
}
