package com.isyscore.robot.core.entity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = "fieldMeta")
public class FieldInfo {

    /**
     * 属性元数据
     */
    private FieldMeta fieldMeta;
    /**
     * 属性对应的jdbcType
     */
    private String fieldType;
    /**
     * 属性的描述
     */
    private String desc;
    /**
     * 时间字段表示位：（0=不是时间，1=时间字段）
     */
    private Integer timeFlag = 0;
    /**
     * 时间字段表示位：（0=不是图片，1=是图片）
     */
    private Integer picFlag = 0;
    /**
     * 枚举字段表示位：（0=不是枚举，1=是枚举）
     */
    private Integer enumFlag = 0;

    public static FieldInfo of(String name, String desc){
        FieldInfo info = new FieldInfo();
        info.setFieldMeta(FieldMeta.of(name));
        info.setDesc(desc);
        return info;
    }

    public static FieldInfo of(String name) {
        return new FieldInfo().setFieldMeta(FieldMeta.of(name));
    }

    public String getDbName(){
        return fieldMeta.getDbName();
    }

    public String getCodeName(){
        return fieldMeta.getCodeName();
    }

    public String getProName(){
        return fieldMeta.getProjectName();
    }
}
