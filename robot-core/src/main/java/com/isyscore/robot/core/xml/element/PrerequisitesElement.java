package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:36 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PrerequisitesElement extends AbstractElementValidator {

    private String maven;
}
