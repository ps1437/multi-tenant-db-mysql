# Multi-Tenant Database with Spring Boot

This project demonstrates how to implement a multi-tenant architecture using Spring Boot, JPA, and MySQL. The tenant is determined based on the `tenantId` present in the request header, with the application using a routing DataSource for tenant-specific connections.

## Features
- **Dynamic DataSource Routing**: Routes requests to the correct database based on `tenantId` in the header.
- **Multi-Tenant Configuration**: Supports multiple databases (`dev`, `perf`) with distinct configurations.
- **JPA Integration**: Configures JPA for tenant-aware entity management.

## Prerequisites
- Java 17+
- Spring Boot 3.x
- MySQL Database
- Maven

## Configuration

### `application.yml`

```yaml
spring:
  jpa:
    properties:
      hibernate:
        hbm2ddl.auto: update
        dialect: org.hibernate.dialect.MySQL8Dialect
        show_sql: true
  datasource:
    url: jdbc:mysql://localhost:3306/dev
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

multi-tenant:
  enabled: true
  default-data-source: perf
  entity-packages:
    - com.syscho.multi
  datasources:
    default:
      url: jdbc:mysql://localhost:3306/dev
      username: root
      password: root
    dev:
      url: jdbc:mysql://localhost:3306/dev
      username: root
      password: root
    perf:
      url: jdbc:mysql://localhost:3306/perf
      username: root
      password: root
```
----
### How It Works
The multi-tenant system allows different tenants to be mapped to different databases. Each request must include a tenantId in the header, which determines the database to route the request to. The application uses a custom RoutingDataSource to switch between different database connections based on the tenantId.

Passing tenantId in HTTP Requests
To use the multi-tenant feature, include the tenantId in the HTTP request header. The application will use the tenantId to route requests to the correct database.

## HTTP Request sample
curl -X GET "http://localhost:8080/api/endpoint" -H "tenantId: dev"

