package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:00 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OsElement extends AbstractElementValidator {

    private String name;
    private String family;
    private String arch;
    private String version;
}
