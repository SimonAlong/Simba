package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:53 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExecutionElement extends AbstractElementValidator {

    private String id;
    private String phase;
    private String goals;
    private String inherited;
    private String configuration;
}
