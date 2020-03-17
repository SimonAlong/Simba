package com.isyscore.robot.integration.exception;

import lombok.Getter;

/**
 * @author shizi
 * @since 2020/3/17 下午5:26
 */
public class BusinessException extends RuntimeException {

    @Getter
    private Integer errCode;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Integer errCode) {
        super();
        this.errCode = errCode;
    }

    public BusinessException(Integer errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(Integer errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }
}
