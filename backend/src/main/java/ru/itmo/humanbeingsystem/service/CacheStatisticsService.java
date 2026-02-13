package ru.itmo.humanbeingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.humanbeingsystem.config.CacheLoggingProperties;

@Service
@RequiredArgsConstructor
public class CacheStatisticsService {

  private final CacheLoggingProperties cacheLoggingProperties;

  public void toggleStatisticsLogging(boolean enabled) {
    cacheLoggingProperties.setLoggingEnabled(enabled);
    System.out.println("Cache statistics logging " + (enabled ? "ENABLED" : "DISABLED"));
  }

  public boolean isStatisticsLoggingEnabled() {
    return cacheLoggingProperties.isLoggingEnabled();
  }
}
