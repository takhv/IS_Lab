package ru.itmo.humanbeingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "ru.itmo.humanbeingsystem.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Autowired
    private Environment env;
    
    // blya vashe nicho ne ponimayu v etih configah

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("database.driver"));
        dataSource.setUrl(env.getProperty("database.url"));
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));
        return dataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("ru.itmo.humanbeingsystem.model");
        
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        // Добавляем JPA свойства из application.properties
        em.setJpaProperties(getJpaProperties());
        
        return em;
    }
    
    private java.util.Properties getJpaProperties() {
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty("javax.persistence.jdbc.driver", env.getProperty("database.driver"));
        properties.setProperty("javax.persistence.jdbc.url", env.getProperty("database.url"));
        properties.setProperty("javax.persistence.jdbc.user", env.getProperty("database.username"));
        properties.setProperty("javax.persistence.jdbc.password", env.getProperty("database.password"));
        properties.setProperty("eclipselink.logging.level", env.getProperty("jpa.properties.eclipselink.logging.level"));
        properties.setProperty("eclipselink.ddl-generation", env.getProperty("jpa.properties.eclipselink.ddl-generation"));
        return properties;
    }
    
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
