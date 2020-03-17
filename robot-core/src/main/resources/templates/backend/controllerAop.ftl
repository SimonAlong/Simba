package ${packagePath}.aop;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.util.TimeRangeStrUtil;
import ${packagePath}.exception.BusinessException;
import ${packagePath}.web.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author ${user}
 * @since ${time}
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    /**
     * 拦截controller中所有的方法
     */
    @Around("execution(* ${packagePath}.web.controller.*.*(..))")
    public Object aroundParameter(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        NeoMap outInfo = NeoMap.of();
        String funStr = pjp.getSignature().toLongString();
        outInfo.put("fun", funStr);

        outInfo.put("parameters", pjp.getArgs());
        Object result;
        try {
            result = pjp.proceed();
            outInfo.put("result", result);
        } catch (Throwable e) {
            outInfo.put("timeout", TimeRangeStrUtil.parseTime(System.currentTimeMillis() - start));
            log.error(outInfo.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                return Response.fail(businessException.getErrCode(), businessException.getMessage());
            } else {
                return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            }
        }
        return result;
    }
}
