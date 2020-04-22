package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:32 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectElement extends AbstractElementValidator {

    private String parent;
    private String modelVersion;
    private String groupId;
    private String artifactId;
    private String packaging;
    private String name;
    private String version;
    private String description;
    private String url;
    private String prerequisites;
    private String issueManagement;
    private String ciManagement;
    private String inceptionYear;
    private String mailingLists;
    private String developers;
    private String contributors;
    private String licenses;
    private String scm;
    private String organization;
    private String build;
    private String profiles;
    private String modules;
    private String repositories;
    private String pluginRepositories;
    private String dependencies;
    private String reports;
    private String reporting;
    private String dependencyManagement;
    private String distributionManagement;
    private String properties;
}
