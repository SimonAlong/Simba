package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:40 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailingListElement extends AbstractElementValidator {

    private String name;
    private String post;
    private String subscribe;
    private String unsubscribe;
    private String archive;
}
