spring:
  profiles:
    active: local
auth:
  api: /${appName}/auth/getAuthListForOs
log:
  home: /home/${appName}/logs
  app: ${appName}
  level: INFO
