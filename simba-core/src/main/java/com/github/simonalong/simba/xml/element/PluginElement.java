package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:52 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PluginElement extends AbstractElementValidator {

    private String groupId;
    private String artifactId;
    private String version;
    private String extensions;
    private String executions;
    private String dependencies;
    private String inherited;
    private String configuration;
    private String reportSets;
}
