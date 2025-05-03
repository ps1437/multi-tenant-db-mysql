package com.syscho.multi.lib;

import com.syscho.multi.lib.properties.MultiTenantProperties;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Objects;

public class DataSourceFactory {

    public static DataSource createDataSource(MultiTenantProperties.TenantConfiguration tenantConfiguration) {
        requireNonNull(tenantConfiguration);

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(tenantConfiguration.getUrl());
        dataSource.setUsername(tenantConfiguration.getUsername());
        dataSource.setPassword(tenantConfiguration.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    private static void requireNonNull(MultiTenantProperties.TenantConfiguration tenantConfiguration) {
        Objects.requireNonNull(tenantConfiguration, "Tenant Configuration cannot be null");
        Objects.requireNonNull(tenantConfiguration.getUrl(), "Database URL must be provided");
        Objects.requireNonNull(tenantConfiguration.getUsername(), "Database username must be provided");
        Objects.requireNonNull(tenantConfiguration.getPassword(), "Database password must be provided");
    }
}
