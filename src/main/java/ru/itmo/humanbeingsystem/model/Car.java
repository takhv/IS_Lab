package ru.itmo.humanbeingsystem.model;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Car {
    private boolean cool;
}
