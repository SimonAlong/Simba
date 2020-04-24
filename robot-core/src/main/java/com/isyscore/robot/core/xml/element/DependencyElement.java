package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 解析标签 {@code <Dependency></Dependency>}
 *
 * @author shizi
 * @since 2020/4/22 6:27 PM
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = true, of={"groupId", "artifactId"})
public class DependencyElement extends AbstractElementValidator {

    private String groupId;
    private String artifactId;
    private String version;
    private String type;
    private String classifier;
    private String scope;
    private String systemPath;
    private String exclusions;
    private String optional;
}
