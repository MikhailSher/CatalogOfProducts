package ru.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 20.12.2020
 * ConfigJpa
 *
 * @author Sherstobitov Mikhail
 * @version v1.0
 */
@Configuration
@ComponentScan(value = "ru.mvc")
@EnableJpaRepositories(basePackages = "ru.mvc.repositories")
@EnableTransactionManagement
public class ConfigJpa {

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setPackagesToScan("ru.mvc.models");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);

        emf.setJpaVendorAdapter(adapter);
        emf.setJpaProperties(jpaProperties());

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQL95Dialect");
        properties.setProperty("hibernate.show_sql",
                "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
}
