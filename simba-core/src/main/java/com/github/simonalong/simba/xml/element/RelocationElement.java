package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:21 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RelocationElement extends AbstractElementValidator {

    private String groupId;
    private String artifactId;
    private String version;
    private String message;
}
