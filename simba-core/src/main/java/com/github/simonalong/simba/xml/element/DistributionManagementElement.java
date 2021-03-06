package com.github.simonalong.simba.xml.element;

import com.github.simonalong.simba.xml.validate.AbstractElementValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2020/4/22 7:16 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DistributionManagementElement extends AbstractElementValidator {

    private String repository;
    private String snapshotRepository;
    private String site;
    private String downloadUrl;
    private String relocation;
    private String status;
}
