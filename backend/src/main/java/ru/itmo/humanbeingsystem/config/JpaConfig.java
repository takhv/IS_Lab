package ru.itmo.humanbeingsystem.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JpaConfig {
  // gospodi pomogi
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("ru.itmo.humanbeingsystem.model");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

    em.setJpaVendorAdapter(vendorAdapter);

    Map<String, Object> properties = new HashMap<>();

    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    return transactionManager;
  }
}
