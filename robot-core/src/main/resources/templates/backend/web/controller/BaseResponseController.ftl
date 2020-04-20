package ${packagePath}.web.controller;

import ${packagePath}.web.vo.Response;
import org.springframework.http.HttpStatus;

/**
 * @author robot
 */
public abstract class BaseResponseController {

    public <T> Response<T> success(T body) {
    return Response.success(body);
    }

    public <T> Response<T> fail(HttpStatus status) {
        return Response.fail(status.toString(), status.getReasonPhrase());
    }

    public <T> Response<T> fail(String errMsg) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errMsg);
    }

    public <T> Response<T> fail(String errCode, String errMsg) {
        return Response.fail(errCode, errMsg);
    }
}
