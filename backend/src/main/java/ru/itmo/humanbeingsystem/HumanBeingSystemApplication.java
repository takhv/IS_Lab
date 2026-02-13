package ru.itmo.humanbeingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HumanBeingSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(HumanBeingSystemApplication.class, args);
  }
}
