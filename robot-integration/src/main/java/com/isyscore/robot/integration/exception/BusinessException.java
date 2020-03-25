package com.isyscore.robot.integration.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author robot
 */
public class BusinessException extends RuntimeException {

    @Getter
    private Integer errCode;

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable e) {
        super(e);
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
