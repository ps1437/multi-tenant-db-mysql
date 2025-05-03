package com.syscho.multi.lib;

import com.syscho.multi.lib.properties.MultiTenantProperties;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties({MultiTenantProperties.class, JpaProperties.class})
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    public TenantRoutingDataSource routingDataSource(MultiTenantProperties tenantProps) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        tenantProps.getDatasources().forEach((tenantId, tenantConfiguration) -> {
            DataSource dataSource = DataSourceFactory.createDataSource(tenantConfiguration);
            dataSourceMap.put(tenantId, dataSource);
        });

        var routingDataSource = new TenantRoutingDataSource();
        routingDataSource.initialize(dataSourceMap, tenantProps.getDefaultDataSource());

        return routingDataSource;
    }


    @Bean
    public DataSource dataSource(TenantRoutingDataSource routingDataSource) {
        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaProperties jpaProperties,
                                                                       MultiTenantProperties tenantProps) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(tenantProps.getEntityPackages().toArray(new String[0]));
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(jpaProperties.getProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }


}
