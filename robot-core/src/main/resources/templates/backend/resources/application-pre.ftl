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
log:
  home: /home/${appName}/logs
  app: ${appName}
  level: INFO
isyscore:
  # API 网关地址：服务网关地址配置, IP 一般为本地 OS 实例部署所在的服务器 网络 IP
  serverHost: http://10.30.30.25:9010/isc-api-gateway
  # APPID：APPID 由平台分配(现阶段开放平台未公开发布, 可通过 UUID.random 生成)
  appId: e56b59fc-6a4d-11ea-ae31-0221860e9b7e
  # 数据交互格式：交互数据描述格式, 暂时仅支持 JSON
  format: JSON
  # 字符集：交互数据字符集, 暂时仅支持 UTF-8
  charset: UTF-8
  # 签名类型：服务访问签名类型, 暂时仅支持 RSA
  signType: RSA
  # 加密类型：服务访问数据加密类型, 暂时仅支持 RSA
  encryptType: RSA
  # 本地操作系统：实例 ID OSID 由平台分配(现阶段开放平台未公开发布, 可通过 UUID.random 生成): OSID 标记了每一个本地 OS 实例的唯一 性, 平台通过 OSID 对所有本地 OS 实例进行管理(包括版本, 升级, 其他服务等支持) 每个本地 OS 实例安装部署自动生成且仅生成一次 OSID.不可 修改与删除.
  # osId: 0
  appCode: ${appName}
  appName: ${appName}
  isMainService: true
  registerAppUrl: http://192.168.10.248:6031/api/application/register

cas:
  app-code: ${appName}
  app-name: ${appNameCn}
  #拦截访问地址规则，多个用英文逗号隔离
  urlPatterns: /${appName}/*
  #不拦截访问地址规则
  excludeUrlPatterns:
    - /${appName}/auth/*
  redis:  # redis 连接配置
    host: 10.30.30.25
    port: 6379
    password: Isysc0re
    database: 7
