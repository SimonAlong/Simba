package com.isyscore.robot.core.xml.element;

import com.isyscore.robot.core.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:41 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeveloperElement extends AbstractElementValidator {

    private String id;
    private String name;
    private String email;
    private String url;
    private String roles;
    private String organization;
    private String organizationUrl;
    private String properties;
    private String timezone;
}
