package com.isyscore.robot.integration.web.vo;

import lombok.Data;

/**
* @author robot
*/
@Data
public class Response<T> {

    private String errCode;
    private String errMsg;
    private T data;

    public static <V> Response<V> success(V data) {
        Response<V> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static <V> Response<V> fail(String errCode, String errMsg) {
        Response<V> response = new Response<>();
        response.setErrCode(errCode);
        response.setErrMsg(errMsg);
        return response;
    }
}
