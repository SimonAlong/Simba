package com.isyscore.robot.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型
 * @author robot
 */
@Getter
@AllArgsConstructor
public enum Enum1Enum {

    /**
     * 成功
     */
    Y("Y"),
    /**
     * 失败
     */
    N("N"),
;

    private String value;
}