package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:38 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CiManagementElement extends AbstractElementValidator {

    private String system;
    private String url;
    private String notifiers;
}
