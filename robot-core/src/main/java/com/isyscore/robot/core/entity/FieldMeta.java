package com.isyscore.robot.core.entity;

import com.isyscore.robot.core.util.Strings;
import com.isyscore.ibo.neo.NeoMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shizi
 * @since 2019/12/3 11:29 下午
 */
@Data
@EqualsAndHashCode(of = "dbName")
public class FieldMeta {

    /**
     * db 中的含有下划线的名字，比如：user_name
     */
    private String dbName;
    /**
     * code 代码中的前缀小写字母，后面小驼峰格式，比如：userName
     */
    private String codeName;
    /**
     * 工程化名字 首字母大写，比如：UserName
     */
    private String projectName;

    private FieldMeta() {}

    public static FieldMeta of(String name) {
        FieldMeta fieldMeta = new FieldMeta();
        fieldMeta.dbName = NeoMap.NamingChg.UNDERLINE.smallCamelToOther(name);
        fieldMeta.codeName = NeoMap.NamingChg.UNDERLINE.otherToSmallCamel(fieldMeta.dbName);
        fieldMeta.projectName = Strings.toCamelCaseAllStrict(fieldMeta.dbName);
        return fieldMeta;
    }
}
