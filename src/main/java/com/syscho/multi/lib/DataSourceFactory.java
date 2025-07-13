package com.syscho.multi.lib;

import com.syscho.multi.lib.properties.MultiTenantProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

public class DataSourceFactory {

    public static DataSource createDataSource(MultiTenantProperties.TenantConfiguration tenantConfiguration) {
        requireNonNull(tenantConfiguration);
        var config = new HikariConfig();
        config.setJdbcUrl(tenantConfiguration.getUrl());
        config.setUsername(tenantConfiguration.getUsername());
        config.setPassword(tenantConfiguration.getPassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(3000);
        config.setIdleTimeout(60000);
        return new HikariDataSource(config);
    }

    private static void requireNonNull(MultiTenantProperties.TenantConfiguration tenantConfiguration) {
        Objects.requireNonNull(tenantConfiguration, "Tenant Configuration cannot be null");
        Objects.requireNonNull(tenantConfiguration.getUrl(), "Database URL must be provided");
        Objects.requireNonNull(tenantConfiguration.getUsername(), "Database username must be provided");
        Objects.requireNonNull(tenantConfiguration.getPassword(), "Database password must be provided");
    }
}
