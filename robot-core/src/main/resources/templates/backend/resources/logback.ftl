<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="APP_NAME" value="${r'${APP_NAME}'}"/>
    <!--<property name="LOG_HOME" value="/home/${r'${APP_NAME}'}/logs"/>-->
    <property name="PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS}[${r'${APP_NAME}'}] [ %thread ] - [ %-5level ] [%X{traceId}] %p %c %M [ %logger{50} : %line ] - %msg%n"/>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${r'${PATTERN}'}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <appender name="FILE_INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${r'${LOG_HOME}'}/app-info.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <encoder>
            <pattern>${r'${PATTERN}'}</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${LOG_HOME}'}/app-info.log.%d{yyyyMMdd}</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

    <appender name="FILE_WARN" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${r'${LOG_HOME}'}/app-warn.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>WARN</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <encoder>
            <pattern>${r'${PATTERN}'}</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${LOG_HOME}'}/app-warn.log.%d{yyyyMMdd}</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

    <appender name="FILE_ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${r'${LOG_HOME}'}/app-error.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <encoder>
            <pattern>${r'${PATTERN}'}</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${r'${LOG_HOME}'}/app-error.log.%d{yyyyMMdd}</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

    <logger name="org.springframework" level="INFO" additivity="false"/>

    <logger name="com.isyscore.isc.neo" level="INFO" additivity="false">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE_INFO" />
        <appender-ref ref="FILE_WARN"/>
        <appender-ref ref="FILE_ERROR" />
    </logger>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE_INFO" />
        <appender-ref ref="FILE_WARN"/>
        <appender-ref ref="FILE_ERROR" />
    </root>

</configuration>

