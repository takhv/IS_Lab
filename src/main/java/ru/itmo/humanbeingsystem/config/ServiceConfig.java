package ru.itmo.humanbeingsystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "ru.itmo.humanbeingsystem.service")
@EnableTransactionManagement
public class ServiceConfig {
    // Конфигурация сервисов
}
