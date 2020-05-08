package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:47 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationElement extends AbstractElementValidator {

    private String name;
    private String url;
}
