package ${packagePath}.aop;

import com.isyscore.isc.mikilin.MkValidators;
import com.isyscore.isc.mikilin.exception.MkCheckException;
import com.isyscore.isc.neo.NeoMap;
import com.isyscore.isc.neo.util.TimeRangeStrUtil;
import ${packagePath}.exception.BusinessException;
import ${packagePath}.web.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author robot
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
            validate(pjp);
            result = pjp.proceed();
        } catch (Throwable e) {
            outInfo.put("errMsg", e.getMessage());
            log.error("后端异常：" + outInfo.toString(), e);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                return Response.fail(businessException.getErrCode(), businessException.getMessage());
            } else {
                return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
            }
        } finally {
            outInfo.put("timeout", TimeRangeStrUtil.parseTime(System.currentTimeMillis() - start));
        }
        return result;
    }

    private void validate(ProceedingJoinPoint pjp) {
        Signature sig = pjp.getSignature();
        MethodSignature methodSignature;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) sig;
        Method currentMethod;
        try {
            currentMethod = pjp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new BusinessException(e);
        }

        if (currentMethod.getDeclaringClass().isAnnotationPresent(AutoCheck.class)) {
            doValidate(pjp);
        } else if (currentMethod.isAnnotationPresent(AutoCheck.class)) {
            doValidate(pjp);
        } else {
            Parameter[] parameters = currentMethod.getParameters();
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(AutoCheck.class)) {
                    try {
                        MkValidators.validate(parameter);
                    } catch (MkCheckException e) {
                        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "参数核查异常：" + MkValidators.getErrMsg());
                    }
                }
            }
        }
    }

    private void doValidate(ProceedingJoinPoint pjp) {
        Object[] parameters = pjp.getArgs();
        for (Object parameter : parameters) {
            try {
                MkValidators.validate(parameter);
            } catch (MkCheckException e) {
                String checkErr = "参数核查异常：" + MkValidators.getErrMsg();
                throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), checkErr);
            }
        }
    }
}
