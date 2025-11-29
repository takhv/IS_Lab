package ru.itmo.humanbeingsystem.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanDTO {
  private Integer id;
  private String name;

  private Integer x;
  private Double y;

  private LocalDate creationDate;
  private boolean realHero;
  private Boolean hasToothpick;

  private Boolean carCool;
  private String carType;

  private String mood;
  private long impactSpeed;
  private String soundtrackName;
  private String weaponType;
}
