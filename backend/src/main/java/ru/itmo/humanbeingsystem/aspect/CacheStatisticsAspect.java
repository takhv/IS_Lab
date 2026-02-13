package ru.itmo.humanbeingsystem.aspect;

import jakarta.persistence.EntityManagerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.itmo.humanbeingsystem.config.CacheLoggingProperties;

@Aspect
@Component
public class CacheStatisticsAspect {

  private final CacheLoggingProperties properties;
  private final EntityManagerFactory entityManagerFactory;
  private final Logger logger = LoggerFactory.getLogger(CacheStatisticsAspect.class);

  public CacheStatisticsAspect(
      CacheLoggingProperties properties, EntityManagerFactory entityManagerFactory) {
    this.properties = properties;
    this.entityManagerFactory = entityManagerFactory;
  }

  @Around("@annotation(ru.itmo.humanbeingsystem.aspect.CacheStatsTracked)")
  public Object logCacheStats(ProceedingJoinPoint joinPoint) throws Throwable {
    if (!properties.isLoggingEnabled()) {
      return joinPoint.proceed();
    }

    Snapshot before = snapshot();
    try {
      Object result = joinPoint.proceed();
      logDelta(joinPoint, before);
      return result;
    } catch (Throwable throwable) {
      logDelta(joinPoint, before);
      throw throwable;
    }
  }

  private Snapshot snapshot() {
    SessionFactoryImplementor sessionFactory =
        entityManagerFactory.unwrap(SessionFactoryImplementor.class);
    Statistics statistics = sessionFactory.getStatistics();
    return new Snapshot(
        statistics.getSecondLevelCacheHitCount(),
        statistics.getSecondLevelCacheMissCount(),
        statistics.getSecondLevelCachePutCount());
  }

  private void logDelta(ProceedingJoinPoint joinPoint, Snapshot before) {
    Snapshot after = snapshot();
    long entityHitDelta = after.entityHits - before.entityHits;
    long entityMissDelta = after.entityMisses - before.entityMisses;
    long entityPutDelta = after.entityPuts - before.entityPuts;

    logger.info(
        String.format(
            "method: [%s] || h%+d, m%+d, p%+d || total (h=%d/m=%d/p=%d); ",
            joinPoint.getSignature().toShortString(),
            entityHitDelta,
            entityMissDelta,
            entityPutDelta,
            after.entityHits,
            after.entityMisses,
            after.entityPuts));
  }

  private record Snapshot(long entityHits, long entityMisses, long entityPuts) {}
}
