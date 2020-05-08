package com.github.simonalong.simba.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
@Data
@Accessors(chain = true)
public class UpdateInsertFieldInfo {

    private FieldInfo fieldInfo;
    /**
     * 属性是否可以更新编辑(0=不能编辑， 1=可以编辑)
     */
    private Integer canEdit = 1;

    public static UpdateInsertFieldInfo of(String name, String desc){
        return new UpdateInsertFieldInfo().setFieldInfo(FieldInfo.of(name, desc));
    }
}
