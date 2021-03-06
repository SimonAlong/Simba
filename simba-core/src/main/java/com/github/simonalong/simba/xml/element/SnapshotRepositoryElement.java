package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:18 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SnapshotRepositoryElement extends AbstractElementValidator {

    private String uniqueVersion;
    private String id;
    private String name;
    private String url;
    private String layout;
}
