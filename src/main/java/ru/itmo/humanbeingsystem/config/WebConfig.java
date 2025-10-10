package ru.itmo.humanbeingsystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.itmo.humanbeingsystem.controller")
public class WebConfig implements WebMvcConfigurer {
    // Веб-конфигурация
}
