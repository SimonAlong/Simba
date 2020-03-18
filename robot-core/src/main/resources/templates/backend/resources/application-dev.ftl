server:
  port: ${backendPort}
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: ${dbUrl}
    username: ${dbUserName}
    password: ${dbUserPassword}
    hikari:
      auto-commit: true
      idle-timeout: 180000
      pool-name: ${appName}.server-HikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
      maximum-pool-size: 10
      minimum-idle: 10
