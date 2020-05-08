package com.github.simonalong.simba.xml.validate;

import com.simonalong.neo.NeoMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shizi
 * @since 2020/4/22 7:59 PM
 */
public class AbstractElementValidator implements Validator {

    protected List<String> fieldNameList = new ArrayList<>();

    protected AbstractElementValidator() {
        Arrays.asList(this.getClass().getDeclaredMethods()).forEach(e -> fieldNameList.add(e.getName()));

        String className = this.getClass().getSimpleName();
        String elementName = className.substring(0, className.length() - "Element".length());
        Validators.put(NeoMap.NamingChg.BIGCAMEL.otherToSmallCamel(elementName), fieldNameList);
    }

    @Override
    public void validate(String element) {
        if (!needValid(element)) {
            return;
        }
        if (!fieldNameList.contains(element)) {
            throw new RuntimeException("标签：" + element + "不包含");
        }
    }

    protected Boolean needValid(String element) {
        return true;
    }
}
