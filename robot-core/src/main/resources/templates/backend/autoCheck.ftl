package ${packagePath}.aop;

import java.lang.annotation.*;

/**
 * 修饰函数和参数，用于属性的核查
 *
 * <p>
 *     <ul>
 *         <li>1.修饰函数：则会核查函数对应的所有参数</li>
 *         <li>2.修饰参数：则只会核查指定的参数</li>
 *     </ul>
 * @author robot
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface AutoCheck {}