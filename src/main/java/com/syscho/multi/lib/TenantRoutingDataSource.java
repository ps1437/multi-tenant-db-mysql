package com.syscho.multi.lib;

import com.syscho.multi.lib.filter.TenantContextHolder;
import com.syscho.multi.lib.properties.MultiTenantProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    private final Map<Object, Object> dynamicDataSources = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getEnv();
    }

    public void initialize(Map<String, DataSource> initialDataSources, String defaultTenant) {
        dynamicDataSources.putAll(initialDataSources);
        super.setTargetDataSources(new ConcurrentHashMap<>(dynamicDataSources));
        super.setDefaultTargetDataSource(initialDataSources.get(defaultTenant));
        super.afterPropertiesSet();
    }
}