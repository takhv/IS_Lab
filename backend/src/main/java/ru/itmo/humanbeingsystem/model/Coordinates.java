package ru.itmo.humanbeingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Coordinates {
  @NotNull private Integer x;

  @NotNull private Double y;
}
