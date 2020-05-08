package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
