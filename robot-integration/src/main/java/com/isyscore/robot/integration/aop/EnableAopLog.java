package com.isyscore.robot.integration.aop;

import java.lang.annotation.*;

/**
 * 添加该注解，则会在切面中，将修饰的方法的出参入参和耗时打印到info日志中
 *
 * @author robot
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAopLog
{}
