package com.syscho.multi.lib;

import com.syscho.multi.lib.filter.TenantIdFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnProperty(name = "tenant.enabled", havingValue = "true")
@Configuration
@Import({DataSourceConfig.class})
@EnableAutoConfiguration(exclude = org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class)
public class MultiDatabaseAutoConfiguration {

    @Bean
    public TenantIdFilter tenantIdFilter() {
        return new TenantIdFilter();
    }
}
