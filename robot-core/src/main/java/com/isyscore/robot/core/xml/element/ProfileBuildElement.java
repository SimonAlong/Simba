package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:04 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileBuildElement extends AbstractElementValidator {

    private String defaultGoal;
    private String resources;
    private String testResources;
    private String directory;
    private String finalName;
    private String filters;
    private String pluginManagement;
    private String plugins;
}
