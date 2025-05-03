package com.syscho.multi.lib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@ConfigurationProperties(prefix = "multi-tenant")
public class MultiTenantProperties {
    private String defaultDataSource;
    private List<String> entityPackages;
    private Map<String, TenantConfiguration> datasources;

    @Getter
    @Setter
    public static class TenantConfiguration {
        private String driverClassName;
        private String url;
        private String username;
        private String password;
    }

}
