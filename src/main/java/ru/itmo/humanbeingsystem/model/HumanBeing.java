package ru.itmo.humanbeingsystem.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "fill name pls")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "kladmen mudak coordinatov net nigde")
    @Embedded
    private Coordinates coordinates;

    @NotNull(message = "no create date")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "real_hero")
    private boolean realHero;

    @Column(name = "tooth_pick")
    private Boolean hasToothpick;

    @Embedded
    private Car car;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood")
    private Mood mood;

    @Column(name = "impact_speed")
    private long impactSpeed;

    @NotNull(message = "soundtrack must not be null")
    @Column(name = "soundtrack_name", nullable = false)
    private String soundtrackName;

    @NotNull(message = "weapon type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type", nullable = false)
    private WeaponType weaponType;


}
