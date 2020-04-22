package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:50 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceElement extends AbstractElementValidator {

    private String targetPath;
    private String filtering;
    private String directory;
    private String includes;
    private String excludes;
}
