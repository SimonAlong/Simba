package com.github.simonalong.simba.xml.validate;

/**
 * @author shizi
 * @since 2020/4/22 7:30 PM
 */
public interface Validator {

    /**
     * 核查对应的元素是否为当前类型
     */
    void validate(String element);
}
