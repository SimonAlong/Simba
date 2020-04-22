package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:48 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BuildElement extends AbstractElementValidator {

    private String sourceDirectory;
    private String scriptSourceDirectory;
    private String testSourceDirectory;
    private String outputDirectory;
    private String testOutputDirectory;
    private String extensions;
    private String defaultGoal;
    private String directory;
    private String finalName;
    private String filters;
    private String pluginManagement;
}
