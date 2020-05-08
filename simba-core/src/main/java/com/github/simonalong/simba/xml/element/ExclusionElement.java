package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:25 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExclusionElement extends AbstractElementValidator {

    private String artifactId;
    private String groupId;
}
