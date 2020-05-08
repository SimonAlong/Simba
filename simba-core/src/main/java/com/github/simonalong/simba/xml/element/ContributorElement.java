package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:44 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContributorElement extends AbstractElementValidator {

    private String name;
    private String email;
    private String url;
    private String organization;
    private String organizationUrl;
    private String roles;
    private String timezone;
    private String properties;
}
