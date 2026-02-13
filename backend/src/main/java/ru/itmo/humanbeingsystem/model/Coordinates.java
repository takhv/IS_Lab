package ru.itmo.humanbeingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Coordinates {
  @NotNull private Integer x;

  @NotNull private Double y;
}
