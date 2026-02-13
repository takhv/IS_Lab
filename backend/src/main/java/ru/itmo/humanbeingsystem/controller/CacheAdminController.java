package ru.itmo.humanbeingsystem.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.itmo.humanbeingsystem.service.CacheStatisticsService;

@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
@Slf4j
public class CacheAdminController {

  private final CacheStatisticsService cacheStatisticsService;

  @PostMapping("/logging")
  public Map<String, String> toggleStatisticsLogging(@RequestParam boolean enabled) {
    cacheStatisticsService.toggleStatisticsLogging(enabled);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Cache statistics logging " + (enabled ? "enabled" : "disabled"));
    response.put("loggingEnabled", String.valueOf(enabled));

    return response;
  }

  @GetMapping("/info")
  public Map<String, Object> getCacheInfo() {
    Map<String, Object> info = new HashMap<>();
    info.put("statisticsLoggingEnabled", cacheStatisticsService.isStatisticsLoggingEnabled());

    return info;
  }
}
