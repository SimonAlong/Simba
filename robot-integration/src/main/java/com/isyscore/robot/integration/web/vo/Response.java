package com.isyscore.robot.integration.web.vo;

import lombok.Data;

/**
* @author zhouzhenyong
* @since 2020/03/17 20:15:13
*/
@Data
public class Response<T> {

    private Integer errCode;
    private String errMsg;
    private T data;

    public static <V> Response<V> success(V data) {
        Response<V> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static <V> Response<V> fail(Integer errCode, String errMsg) {
        Response<V> response = new Response<>();
        response.setErrCode(errCode);
        response.setErrMsg(errMsg);
        return response;
    }
}
