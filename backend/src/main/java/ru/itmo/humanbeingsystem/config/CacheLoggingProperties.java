package ru.itmo.humanbeingsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.cache.stats")
@Getter
@Setter
public class CacheLoggingProperties {
  private boolean loggingEnabled = true;
}
