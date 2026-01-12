package ru.itmo.humanbeingsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Car {
  private Boolean cool;
  private String carType;
}
