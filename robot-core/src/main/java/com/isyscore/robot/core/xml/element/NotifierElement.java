package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:39 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotifierElement extends AbstractElementValidator {

    private String type;
    private String sendOnError;
    private String sendOnFailure;
    private String sendOnSuccess;
    private String sendOnWarning;
    private String address;
    private String configuration;
}
