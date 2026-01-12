package ru.itmo.humanbeingsystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HumanUpdateDTO {
  @NotNull private Integer id;

  @NotNull @NotEmpty private String name;

  @NotNull private Integer x;
  @NotNull private Double y;

  private boolean realHero;
  private Boolean hasToothpick;

  private Boolean carCool;
  private String carType;

  private String mood;
  private long impactSpeed;

  @NotNull private String soundtrackName;

  @NotNull private String weaponType;
}
