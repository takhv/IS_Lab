package ru.itmo.humanbeingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "human_being")
public class HumanBeing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @NotNull
  @NotEmpty
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull @Embedded private Coordinates coordinates;

  @NotNull
  @Column(name = "creation_date", nullable = false)
  private LocalDate creationDate = LocalDate.now();

  @Column(name = "real_hero")
  private boolean realHero;

  @Column(name = "tooth_pick")
  private Boolean hasToothpick;

  @Embedded private Car car;

  @Enumerated(EnumType.STRING)
  @Column(name = "mood")
  private Mood mood;

  @Column(name = "impact_speed")
  private long impactSpeed;

  @NotNull
  @Column(name = "soundtrack_name", nullable = false)
  private String soundtrackName;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "weapon_type", nullable = false)
  private WeaponType weaponType;
}
