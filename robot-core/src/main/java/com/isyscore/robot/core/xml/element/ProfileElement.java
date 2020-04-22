package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:58 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileElement extends AbstractElementValidator {

    private String id;
    private String properties;
    private String activation;
    private String build;
    private String modules;
    private String repositories;
    private String pluginRepositories;
    private String dependencies;
    private String reports;
    private String reporting;
    private String dependencyManagement;
    private String distributionManagement;
}
