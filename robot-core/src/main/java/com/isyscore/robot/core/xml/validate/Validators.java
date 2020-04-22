package com.isyscore.robot.core.xml.validate;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shizi
 * @since 2020/4/22 7:57 PM
 */
@UtilityClass
public class Validators {

    private static Map<String, List<String>> allElementMap = new HashMap<>(64);

    public void put(String element, List<String> childElementList) {
        allElementMap.put(element, childElementList);
    }

    public void contain(String element, String childElement) {
        if (!allElementMap.containsKey(element)) {
            throw new RuntimeException("标签：" + element + "不是母标签");
        }

        if (!allElementMap.get(element).contains(childElement)) {
            throw new RuntimeException("标签：<" + element + "> 不包含子标签 <" + childElement + ">");
        }
    }
}
