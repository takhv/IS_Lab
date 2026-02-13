package ru.itmo.humanbeingsystem.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateStatisticsConfig {

  @Autowired private EntityManagerFactory entityManagerFactory;

  @PostConstruct
  public void enableStatistics() {
    SessionFactoryImplementor sessionFactory =
        entityManagerFactory.unwrap(SessionFactoryImplementor.class);
    Statistics statistics = sessionFactory.getStatistics();
    statistics.setStatisticsEnabled(true);
  }
}
