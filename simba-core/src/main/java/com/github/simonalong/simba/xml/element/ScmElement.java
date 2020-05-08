package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 6:46 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScmElement extends AbstractElementValidator {

    private String connection;
    private String developerConnection;
    private String tag;
    private String url;
}
