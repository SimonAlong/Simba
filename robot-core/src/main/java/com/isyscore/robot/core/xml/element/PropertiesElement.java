package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:59 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PropertiesElement extends AbstractElementValidator {

    private String env;
    private String dept;

    @Override
    protected Boolean needValid(String element) {
        return false;
    }
}
