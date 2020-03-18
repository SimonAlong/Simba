package com.isyscore.robot.core;

import com.isyscore.ibo.neo.codegen.EntityCodeGen;
import com.isyscore.robot.core.entity.*;
import com.isyscore.robot.core.util.*;
import com.isyscore.ibo.neo.Neo;
import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.db.NeoColumn;
import freemarker.template.TemplateException;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 前后端生成器
 *
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
@Setter
@SuppressWarnings("unchecked")
public class CodeGen {

    /**
     * 应用名字，用于前端创建目录用，建议用一个小写的单词
     */
    private String appName;
    /**
     * mysql的时间字段
     */
    private static final List<String> mysqlTimeType = Arrays.asList("DATETIME", "TIMESTAMP");

    /**
     * mysql的枚举类型
     */
    private static final String mysqlEnumType = "ENUM";

    private static final String FRONT_PRE = "front/";
    private static final String BACKEND_PRE = "backend/";

    /**
     * 前端代码生成路径
     */
    private String frontCodePath;
    /**
     * 后端代码的项目模块路径
     */
    private String projectModelPath;
    /**
     * 后端代码生成路径
     */
    private String backendCodePath;
    /**
     * 后端资源文件生成路径
     */
    private String backendResourcesPath;
    /**
     * 后端项目的包路径
     */
    private String packagePath;
    /**
     * 表名前缀
     */
    private String preFix;
    /**
     * 表名
     */
    @Setter
    private String tableName;
    /**
     * 表的描述
     */
    @Setter
    private String tableDesc;
    /**
     * 不需要生成的表
     */
    private String dbUrl;
    private String dbUserName;
    private String dbUserPassword;
    /**
     * 是否直连：前后端直连，不是走代理模式
     */
    private Boolean direct = false;
    /**
     * 前端配置的后端的端口
     */
    private String backendPort;
    /**
     * 表的属性为时间类型的字段
     */
    private Map<String, FieldMeta> tableTimeFieldMap = new HashMap<>();
    /**
     * 属性名和界面展示的中文映射，如果没有指定，则用数据库的注释
     * tableName, fieldName, fieldDesc
     */
    private Map<String, FieldInfo> tableFieldNameMap = new HashMap<>();
    /**
     * "新增弹窗"展示的字段
     * tableName, fieldName
     */
    private Map<String, FieldMeta> insertFieldsMap = new HashMap<>();
    /**
     * "修改弹窗"（即编辑）展示的字段
     * tableName, fieldName
     */
    private Map<String, FieldMeta> updateFieldsMap = new HashMap<>();
    /**
     * "修改弹窗"（即编辑）中不可编辑的属性，针对{@code updateFieldMap}中展示的属性进行禁用
     * tableName, fieldName
     */
    private Map<String, FieldMeta> unEditFieldsMap = new HashMap<>();
    /**
     * 界面上所有位置都不展示的字段
     */
    private Map<String, FieldMeta> excludesFieldsMap = new HashMap<>();
    /**
     * 搜索框搜索的字段
     */
    private Map<String, FieldMeta> queryFieldsMap = new HashMap<>();
    /**
     * "表格"中展示的属性
     * tableName, fieldName
     */
    private Map<String, FieldMeta> tableShowFieldsMap = new HashMap<>();
    /**
     * "表格"中展示的属性
     * tableName, fieldName
     */
    private Map<String, FieldMeta> tableShowExpandFieldsMap = new HashMap<>();

    public void setBackendModulePath(String codePath) {
        this.projectModelPath = codePath;
        if (codePath.endsWith("/")) {
            this.backendCodePath = codePath + "src/main/java/";
            this.backendResourcesPath = codePath + "src/main/resources/";
        } else {
            this.backendCodePath = codePath + "/src/main/java/";
            this.backendResourcesPath = codePath + "/src/main/resources/";
        }
    }

    public void setBackendPackage(String packagePath) {
        this.packagePath = packagePath;
        this.backendCodePath += packagePath.replace(".", "/") + "/";
    }

    /**
     * 设置表的属性和中文名的对应
     *
     * @param tableFieldMap fieldName-fieldDesc
     */
    public void setFieldNameMap(Map<String, String> tableFieldMap) {
        if (null == tableFieldMap || tableFieldMap.size() == 0) {
            return;
        }
        tableFieldMap.forEach((k, v) -> tableFieldNameMap.put(k, FieldInfo.of(k, v)));
    }

    /**
     * 设置弹窗"新增"中展示的字段
     *
     * @param fields fieldList
     */
    public void setInsertFields(String... fields) {
        generateMap(insertFieldsMap, fields);
    }

    public void setUpdateFields(String... fields) {
        generateMap(updateFieldsMap, fields);
    }

    public void setUnEditFields(String... fields) {
        generateMap(unEditFieldsMap, fields);
    }

    public void setExcludesFields(String... fields) {
        generateMap(excludesFieldsMap, fields);
    }

    public void setQueryFields(String... fields) {
        generateMap(queryFieldsMap, fields);
    }

    public void setTableShowFieldsMap(String... fields) {
        generateMap(tableShowFieldsMap, fields);
    }

    public void setTableExpandFieldsMap(String... fields) {
        generateMap(tableShowExpandFieldsMap, fields);
    }

    private void generateMap(Map<String, FieldMeta> dataMap, String... fields) {
        if (null == fields || fields.length == 0) {
            return;
        }

        Stream.of(fields).forEach(f -> dataMap.put(f, FieldMeta.of(f)));
    }

    /**
     * config_group -> ConfigGroup
     */
    private String getTablePathName(String tableName) {
        return Strings.toCamelCaseAll(tableName);
    }

    /**
     * config_group -> config/group
     */
    private String getTableUrlName(String tableName) {
        return tableName.replaceAll("_", "/");
    }

    /**
     * config_group -> configGroup
     */
    private String getTablePathNameLower(String tableName) {
        return Strings.toCamelCaseStrict(tableName);
    }

    /**
     * config_group -> configgroup
     */
    private String getTablePathSplitLower(String tableName) {
        return Strings.toCamelCaseStrict(tableName);
    }

    /**
     * 将表中文名和表名对应起来
     */
    private void configTableName(NeoMap dataMap) {
        dataMap.put("tableNameCn", tableName);
        if (null != tableDesc) {
            dataMap.put("tableNameCn", tableDesc);
        }
    }

    /**
     * 添加枚举类型和对应的值
     */
    private void configEnumTypeField(NeoMap dataMap, List<NeoColumn> columns) {
        List<EnumInfo> infoList = new ArrayList<>();
        if (null != columns && !columns.isEmpty()) {
            columns.stream()
                .filter(c -> c.getColumnTypeName().equals(mysqlEnumType))
                .forEach(c -> infoList.add(EnumInfo.of(c.getColumnName(), getEnumValueList(c.getInnerColumn().getRemarks()))));
        }
        dataMap.put("enumFields", infoList);
    }

    /**
     * 根据时间字段表，将各个表中的时间字段在界面上进行转换
     */
    private void configTimeField(List<NeoColumn> columns) {
        if (null != columns && !columns.isEmpty()) {
            columns.forEach(c -> {
                if (mysqlTimeType.contains(c.getColumnTypeName())) {
                    tableTimeFieldMap.computeIfAbsent(c.getColumnName(), FieldMeta::of);
                }
            });
        }
    }

    /**
     * 设置表的属性名和名称的对应，如果没有设置，则用DB中的注释，如果注释也没有，则直接用name
     */
    private void configFieldName(List<NeoColumn> columns) {
        if (null == columns || columns.isEmpty()) {
            return;
        }

        columns.forEach(column -> tableFieldNameMap.computeIfAbsent(column.getColumnName(), k -> FieldInfo.of(k, getRemark(column))));
    }

    /**
     * 配置新增弹窗要展示的字段
     * <p>
     * 注意：如果有这么几个基本字段则这里默认在添加框中不展示
     */
    private void configInsertField(NeoMap dataMap, List<NeoColumn> columns) {
        if (insertFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }
        List<String> dbFieldList = insertFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<UpdateInsertFieldInfo> insertFieldInfos = columns.stream()
            .filter(column -> !column.getColumnName().equals("id"))
            .filter(column -> !column.getColumnName().equals("create_time"))
            .filter(column -> !column.getColumnName().equals("update_time"))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbFieldName = column.getColumnName();
                UpdateInsertFieldInfo info = UpdateInsertFieldInfo.of((dbFieldName), getFieldDesc(dbFieldName, column.getInnerColumn().getRemarks()));

                // 设置时间类型
                if (fieldIsTimeField(dbFieldName)) {
                    info.getFieldInfo().setTimeFlag(1);
                }

                // 设置枚举类型
                if (column.getColumnTypeName().equals(mysqlEnumType)) {
                    info.getFieldInfo().setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("insertFields", insertFieldInfos);
    }

    /**
     * 设置哪些字段是可以更新的，首先过滤排除表，然后查看展示表
     */
    private void configUpdateField(NeoMap dataMap, List<NeoColumn> columns) {
        if (updateFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = updateFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<UpdateInsertFieldInfo> fieldInfos = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(c -> {
                String dbName = c.getColumnName();
                UpdateInsertFieldInfo info = UpdateInsertFieldInfo.of(dbName, getFieldDesc(dbName, c.getInnerColumn().getRemarks())).setCanEdit(1);

                // 设置哪些字段是只可查看不可编辑
                if (fieldIsUnEdit(dbName)) {
                    info.setCanEdit(0);
                }

                // 设置时间类型
                if (fieldIsTimeField(dbName)) {
                    info.getFieldInfo().setTimeFlag(1);
                }

                // 设置枚举类型
                if (c.getColumnTypeName().equals(mysqlEnumType)) {
                    info.getFieldInfo().setEnumFlag(1);
                }

                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("updateFields", fieldInfos);
    }

    private void configSearchField(NeoMap dataMap, List<NeoColumn> columns) {
        if (queryFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = queryFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<FieldInfo> searchFieldMapList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("searchFields", searchFieldMapList);
    }

    private void configTableShowField(NeoMap dataMap, List<NeoColumn> columns) {
        if (tableShowFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = tableShowFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<FieldInfo> fieldInfoList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("tableShowFields", fieldInfoList);
    }

    /**
     * 设置表的每一行展开字段，排除表格的字段，排除不展示的字段，其他的字段都进行展示
     */
    private void configExpandShowField(NeoMap dataMap, List<NeoColumn> columns) {
        if (tableShowExpandFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = tableShowExpandFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<FieldInfo> fieldInfoList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());

        dataMap.put("expandExist", 1);
        dataMap.put("expandFields", ListUtils.split(fieldInfoList, 4));
    }

    private void configInsertEntity(NeoMap dataMap, List<NeoColumn> columns) {
        if (insertFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        NeoMap importMap = NeoMap.of();
        preGenerateImport(importMap);
        List<String> dbFieldList = insertFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<FieldInfo> fieldInfoList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 属性类型转换
                fieldTypeChg(column, info);

                generateImport(column, importMap);

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("insertReqFields", fieldInfoList);
        dataMap.put("insertReqImport", importMap);
    }

    private void configUpdateEntity(NeoMap dataMap, List<NeoColumn> columns) {
        if (updateFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = updateFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        NeoMap importMap = NeoMap.of();
        preGenerateImport(importMap);
        List<FieldInfo> fieldInfoList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 属性类型转换
                fieldTypeChg(column, info);

                // 配置实体中配置的引用
                generateImport(column, importMap);

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("updateReqFields", fieldInfoList);
        dataMap.put("updateReqImport", importMap);
    }

    private void configQueryReqEntity(NeoMap dataMap, List<NeoColumn> columns) {
        if (queryFieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> dbFieldList = queryFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());

        NeoMap importMap = NeoMap.of();
        preGenerateImport(importMap);
        List<FieldInfo> fieldInfoList = columns.stream()
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .map(column -> {
                String dbName = column.getColumnName();
                FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

                // 属性类型转换
                fieldTypeChg(column, info);

                // 配置实体中配置的引用
                generateImport(column, importMap);

                // 时间戳设置
                if (fieldIsTimeField(info.getCodeName())) {
                    info.setTimeFlag(1);
                }

                // 枚举类型设置
                if (fieldIsEnum(columns, dbName)) {
                    info.setEnumFlag(1);
                }
                return info;
            })
            .collect(Collectors.toList());
        dataMap.put("queryReqFields", fieldInfoList);
        dataMap.put("queryReqImport", importMap);
    }

    private void configQueryRspEntity(NeoMap dataMap, List<NeoColumn> columns) {
        if (null == columns || columns.isEmpty()) {
            return;
        }

        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        NeoMap importMap = NeoMap.of();
        preGenerateImport(importMap);
        List<FieldInfo> fieldInfoList = columns.stream().filter(column -> !excludeFieldList.contains(column.getColumnName())).map(column -> {
            String dbName = column.getColumnName();
            FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));

            // 属性类型转换
            fieldTypeChg(column, info);

            // 配置实体中配置的引用
            generateImport(column, importMap);

            // 时间戳设置
            if (fieldIsTimeField(info.getCodeName())) {
                info.setTimeFlag(1);
            }

            // 枚举类型设置
            if (fieldIsEnum(columns, dbName)) {
                info.setEnumFlag(1);
            }
            return info;
        }).collect(Collectors.toList());
        dataMap.put("queryRspFields", fieldInfoList);
        dataMap.put("queryRspImport", importMap);
    }

    // 时间类型转换
    private void fieldTypeChg(NeoColumn column, FieldInfo info) {
        String type = column.getJavaClass().getSimpleName();
        if (type.equals("BigInteger")) {
            // 针对DB中的BigInteger类型，这里采用Long类型进行转换
            info.setFieldType("Long");
            return;
        } else {
            info.setFieldType(type);
        }

        if (type.equals("Timestamp") || type.equals("Time") || type.equals("Year")) {
            // 针对DB中的时间类型，这里全部采用Data类型
            info.setFieldType("Date");
        } else {
            info.setFieldType(type);
        }
    }

    /**
     * 获取属性的描述，比如：user_name -> 用户名
     *
     * @param fieldName    db中对应的属性的名字
     * @param defaultValue 如果没有配置，则采用默认的名字
     * @return 属性对应的中文名
     */
    private String getFieldDesc(String fieldName, String defaultValue) {
        if (null != fieldName && !"".equals(fieldName)) {
            if (tableFieldNameMap.containsKey(fieldName)) {
                FieldInfo fieldInfo = tableFieldNameMap.get(fieldName);
                if (null != fieldInfo) {
                    String desc = fieldInfo.getDesc();
                    if (StringUtils.isNoneBlank(desc)) {
                        return desc;
                    }
                }
            }
        }
        if (StringUtils.isNoneBlank(defaultValue)) {
            return defaultValue;
        }
        return fieldName;
    }

    private void configBackend(NeoMap dataMap) {
        if (null == backendPort) {
            dataMap.put("backendPort", backendPort);
        }
    }

    private String getRemark(NeoColumn c) {
        String remarks = c.getInnerColumn().getRemarks();
        if (null != remarks) {
            if (c.getColumnTypeName().equals(mysqlEnumType)) {
                return getEnumDesc(remarks);
            }
            return remarks;
        }
        return c.getColumnName();
    }

    /**
     * 判断字段是否为枚举类型
     */
    private boolean fieldIsEnum(List<NeoColumn> columns, String field) {
        if (null != columns && !columns.isEmpty()) {
            return columns.stream().anyMatch(c -> c.getColumnName().equals(field) && c.getColumnTypeName().equals(mysqlEnumType));
        }
        return false;
    }

    /**
     * 对于枚举类型，获取其中枚举类型的描述
     *
     * @param fieldDesc 性别用户的性别:MALE=男性;FEMALE=女性;UNKNOWN=未知
     * @return 性别用户的性别
     */
    private String getEnumDesc(String fieldDesc) {
        if (null == fieldDesc) {
            return null;
        }

        return getEnumDesc(fieldDesc, Arrays.asList(":", "：", ",", "，"));
    }

    private String getEnumDesc(String fieldDesc, List<String> splitStrs) {
        for (String splitStr : splitStrs) {
            if (fieldDesc.contains(splitStr)) {
                return Arrays.asList(fieldDesc.split(splitStr)).get(0);
            }
        }
        return fieldDesc;
    }


    private Boolean fieldIsUnEdit(String fieldDbName) {
        if (null != unEditFieldsMap) {
            return unEditFieldsMap.containsKey(fieldDbName);
        }
        return true;
    }

    /**
     * 获取枚举值的key和value
     *
     * @param str 比如：性别用户的性别:MALE=男性;FEMALE=女性;UNKNOWN=未知
     * @return {MALE:男性, FEMAIL=女性, UNKNOWN=未知}
     */
    private List<EnumMeta> getEnumValueList(String str) {
        if (null == str) {
            return null;
        }

        return getEnumValueList(str, Arrays.asList(";", ",", "，"));
    }

    private List<EnumMeta> getEnumValueList(String str, List<String> splitStrs) {
        for (String splitStr : splitStrs) {
            if (str.contains(splitStr)) {
                return getEnumValueListFromSemi(str, splitStr);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 判断表的某个属性是否为时间类型
     */
    private boolean fieldIsTimeField(String field) {
        if (null != tableTimeFieldMap) {
            return tableTimeFieldMap.containsKey(field);
        }
        return false;
    }

    /**
     * 分号
     */
    private List<EnumMeta> getEnumValueListFromSemi(String string, String splitStr) {
        List<EnumMeta> metaLis = new ArrayList<>();
        List<String> valueList = Arrays.asList(string.split(splitStr));
        if (!valueList.isEmpty()) {
            valueList.forEach(v -> {
                String key = getKey(v);
                String value = getValue(v);
                if (null != key) {
                    if (null != value) {
                        metaLis.add(EnumMeta.of(key, value));
                    } else {
                        metaLis.add(EnumMeta.of(key, key));
                    }
                }
            });
        }
        return metaLis;
    }


    /**
     * = 好前面的，逗号或者分号之间的字符
     */
    private String getKey(String value) {
        Integer endIndex = value.indexOf("=");
        return getKeyFromSplit(value, Arrays.asList(":", "：", ",", "，"), endIndex);
    }

    private String getKeyFromSplit(String value, List<String> splitStrs, Integer endIndex) {
        for (String splitStr : splitStrs) {
            int index = value.indexOf(splitStr);
            if (-1 != index && -1 != endIndex) {
                return value.substring(index + 1, endIndex);
            }
        }
        if (-1 != endIndex) {
            return value.substring(0, endIndex);
        }
        return null;
    }

    /**
     * 等号后面的字符
     */
    private String getValue(String value) {
        int index = value.indexOf("=");
        if (-1 != index) {
            return value.substring(index + 1);
        }
        return null;
    }

    /**
     * 去除前缀：lk_config_group -> config_group
     */
    private String excludePreFix() {
        if (null != preFix && tableName.startsWith(preFix)) {
            return tableName.substring(preFix.length());
        }
        return tableName;
    }

    /**
     * 菜单的路径配置文件
     */
    private void writeRouterConfig(NeoMap dataMap, String filePath) {
        try {
            String oldRouterConfigText = FileUtil.read(filePath);
            String dbName = String.valueOf(dataMap.get("appName"));
            Map<String, String> componentInfoMap = (Map<String, String>) dataMap.get("tableComponentInfos");

            if (null != componentInfoMap && !componentInfoMap.isEmpty()) {
                Triple tripleList = new MutableTriple<>(dbName, componentInfoMap.get("tableName"), componentInfoMap.get("tablePathName"));

                // 获取表名和表中文名对应的map
                Map<String, String> tableNameDescMap = new HashMap<>();
                tableNameDescMap.put(tableName, tableDesc);

                FileUtil.write(filePath, addConfigRouter(tableNameDescMap, oldRouterConfigText, tripleList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增菜单的路由
     *
     * @param oldRouterConfigText 旧的配置文件路径
     * @param tableInfo:          里面包括：db 库名字，用于路径; tableName 表名，两个小写拼接; tablePathName 表路径名
     * @return 新增菜单后的路由菜单
     */
    private String addConfigRouter(Map<String, String> tableNameDescMap, String oldRouterConfigText, Triple tableInfo) {
        String endStr = "" + "      // 403\n" + "      {\n" + "        component: '403',\n" + "      },\n" + "      // 404\n" + "      {\n" + "        component: '404',\n" + "      },\n" + "    ],\n" + "  },\n" + "];";
        int inputStartIndex = oldRouterConfigText.indexOf(endStr);
        if (-1 != inputStartIndex) {
            StringBuilder sb = new StringBuilder(oldRouterConfigText.substring(0, inputStartIndex));
            StringBuilder tem = new StringBuilder();
            String tableName = (String) tableInfo.getMiddle();
            if (tableNameDescMap.containsKey(tableName)) {
                tem.append("      // ").append(tableNameDescMap.get(tableName)).append("\n");
            }
            tem.append("      {\n")
                .append("        path: '/")
                .append(tableInfo.getMiddle())
                .append("',\n")
                .append("        name: '")
                .append(tableInfo.getMiddle())
                .append("List',\n")
                .append("        icon: 'lock',\n")
                .append("        // 直连阶段先删除，接入权限时候放开即可\n")
                .append("        // authority: ['admin', '")
                .append(tableInfo.getMiddle())
                .append("List'],\n")
                .append("        component: './")
                .append(tableInfo.getLeft())
                .append("/")
                .append(tableInfo.getRight())
                .append("List',\n")
                .append("      },\n");

            // 如果已经存在则不添加
            if (!oldRouterConfigText.contains(tem.toString())) {
                sb.append(tem.toString());
            }
            sb.append(endStr);
            return sb.toString();
        }
        return oldRouterConfigText;
    }

    /**
     * 菜单的路径配置文件
     */
    private void writeMenu(NeoMap dataMap, String filePath) {
        try {
            String oldMenuText = FileUtil.read(filePath);
            TableInfo tableInfo = (TableInfo) dataMap.get("tableInfo");
            FileUtil.write(filePath, addMenu(oldMenuText, tableInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向文件中后面添加菜单
     *
     * @param oldMenuText 菜单源码中的旧数据
     * @return 返回加入后的菜单数据
     */
    private String addMenu(String oldMenuText, TableInfo tableInfo) {
        String endStr = "};";
        int inputStartIndex = oldMenuText.indexOf(endStr);
        if (-1 != inputStartIndex) {
            StringBuilder sb = new StringBuilder(oldMenuText.substring(0, inputStartIndex));
            StringBuilder tem = new StringBuilder();
            tem.append("  'menu.").append(tableInfo.getName()).append("List': '").append(tableInfo.getDesc()).append("',\n");

            // 如果已经存在则不添加
            if (!oldMenuText.contains(tem.toString())) {
                sb.append(tem.toString());
            }
            sb.append(endStr);
            return sb.toString();
        }
        return oldMenuText;
    }

    private void configTableMenu(NeoMap dataMap) {
        dataMap.put("tableInfo", TableInfo.of(getTablePathSplitLower(excludePreFix()), tableDesc));

        String tableNameAfterPre = excludePreFix();
        dataMap.put("tableComponentInfos", NeoMap.of().append("tableName", getTablePathSplitLower(tableNameAfterPre)).append("tablePathName", getTablePathName(tableNameAfterPre)));
    }

    private NeoMap generateBaseBone() {
        NeoMap dataMap = new NeoMap();

        dataMap.put("backendPort", backendPort);
        dataMap.put("appName", appName);

        // 设置后端信息（端口和url）
        configBackend(dataMap);

        // 配置所有表和表名的对应
        configTableMenu(dataMap);
        return dataMap;
    }

    private NeoMap generateFrontBone() {
        NeoMap dataMap = generateBaseBone();

        if (null != frontCodePath) {
            // router.config.js
            writeRouterConfig(dataMap, frontCodePath + "/config/router.config.js");

            // menu.js
            writeMenu(dataMap, frontCodePath + "/src/locales/zh-CN/menu.js");
        }
        return dataMap;
    }

    private NeoMap generateBackendBone() {
        return generateBaseBone();
    }

    private void configBaseInfo(NeoMap dataMap, String tableNameAfterPre) {
        // 表格扩展先设置为不显示
        dataMap.put("expandExist", 0);
        dataMap.put("tablePathName", getTablePathName(tableNameAfterPre));
        dataMap.put("tableUrlName", getTableUrlName(tableNameAfterPre));
        dataMap.put("tablePathNameLower", getTablePathNameLower(tableNameAfterPre));

        dataMap.put("tablePathSplitLower", getTablePathSplitLower(tableNameAfterPre));
    }

    private void configClassHead(NeoMap dataMap) {
        dataMap.put("user", System.getProperty("user.name"));
        dataMap.put("time", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
    }

    private void configDBInfo(NeoMap dataMap) {
        if (null == dbUrl || null == dbUserName || null == dbUserPassword) {
            return;
        }
        Neo neo = Neo.connect(dbUrl, dbUserName, dbUserPassword);
        List<NeoColumn> columns = neo.getColumnList(tableName);

        //****** 设置表基本信息 ******
        configTableName(dataMap);
        configEnumTypeField(dataMap, columns);
        configTimeField(columns);
        configFieldName(columns);

        //****** 设置"新增框"信息 ******
        configInsertField(dataMap, columns);

        //****** 设置"编辑框"信息 ******
        configUpdateField(dataMap, columns);

        //****** 设置"搜索框"信息 ******
        configSearchField(dataMap, columns);

        //****** 设置"表格展示"信息 ******
        configTableShowField(dataMap, columns);

        //****** 设置"表格的扩展"信息 ******
        configExpandShowField(dataMap, columns);

        //****** 设置后端"CURD"实体对应的字段 ******
        configInsertEntity(dataMap, columns);
        configUpdateEntity(dataMap, columns);
        configQueryReqEntity(dataMap, columns);
        configQueryRspEntity(dataMap, columns);
    }

    private void preGenerateImport(NeoMap importMap) {
        importMap.put("importDate", 0);
        importMap.put("importTime", 0);
        importMap.put("importTimestamp", 0);
        importMap.put("importBigDecimal", 0);
    }

    /**
     * 根据属性的Java类型映射，进行类型的判断和生成
     */
    @SuppressWarnings("all")
    private void generateImport(NeoColumn column, NeoMap importMap) {
        // 先将标示清理掉
        Class fieldClass = column.getJavaClass();
        if (java.sql.Date.class.isAssignableFrom(fieldClass) || java.sql.Time.class.isAssignableFrom(fieldClass) || java.sql.Timestamp.class.isAssignableFrom(fieldClass)) {
            importMap.put("importDate", 1);
        }

        if (java.math.BigDecimal.class.isAssignableFrom(fieldClass)) {
            importMap.put("importBigDecimal", 1);
        }

        // 枚举类型
        if ("ENUM".equals(column.getColumnTypeName())) {
            importMap.put("enumFlag", 1);
        }
    }

    private void writeFile(NeoMap dataMap, String filePath, String templateName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileUtil.getFile(filePath)));
            Objects.requireNonNull(FreeMarkerTemplateUtil.getTemplate(templateName)).process(dataMap, bufferedWriter);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBaseResponseController(NeoMap dataMap) {
        String adminConstantFullPath = backendCodePath + "web/controller/BaseResponseController.java";
        if (!FileUtil.exist(adminConstantFullPath)) {
            // baseResponseController
            writeFile(dataMap, adminConstantFullPath, BACKEND_PRE + "baseResponseController.ftl");
        }
    }

    private void writeResponse(NeoMap dataMap) {
        String adminConstantFullPath = backendCodePath + "constants/AdminConstant.java";
        if (!FileUtil.exist(adminConstantFullPath)) {
            writeFile(dataMap, backendCodePath + "web/vo/Response.java", BACKEND_PRE + "response.ftl");
        }
    }

    public void generateFront() {
        NeoMap dataMap = generateFrontBone();
        dataMap.put("tableName", tableName);
        dataMap.put("expandExist", 1);

        String tableNameAfterPre = excludePreFix();
        configBaseInfo(dataMap, tableNameAfterPre);
        configDBInfo(dataMap);

        // List
        writeFile(dataMap, frontCodePath + "/src/pages/" + appName + "/" + getTablePathName(tableNameAfterPre) + "List.js", FRONT_PRE + "tableList.ftl");
        writeFile(dataMap, frontCodePath + "/src/pages/" + appName + "/" + getTablePathName(tableNameAfterPre) + "List.less", FRONT_PRE + "tableList.less");
        // model
        writeFile(dataMap, frontCodePath + "/src/models/" + appName + "/" + getTablePathNameLower(tableNameAfterPre) + "Model.js", FRONT_PRE + "tableModel.ftl");
        // api
        writeFile(dataMap, frontCodePath + "/src/services/" + appName + "/" + getTablePathNameLower(tableNameAfterPre) + "Api.js", FRONT_PRE + "tableApi.ftl");

        if (direct) {
            writeFile(dataMap, frontCodePath + "/config/config.js", FRONT_PRE + "frontConfig.ftl");
        }

        System.out.println("front generate finish");
    }

    public void generateBackend() {
        NeoMap dataMap = generateBackendBone();
        dataMap.put("tableName", tableName);
        dataMap.put("packagePath", packagePath);

        String tableNameAfterPre = excludePreFix();
        // 配置表路径的一些基本信息
        configBaseInfo(dataMap, tableNameAfterPre);
        // 配置db对应的实体信息
        configDBInfo(dataMap);
        // 配置类头部注释
        configClassHead(dataMap);

        generateAop(dataMap);
        generateConfig(dataMap);
        generateDao(dataMap, tableNameAfterPre);
        generateException(dataMap);
        generateEntity();
        generateService(dataMap, tableNameAfterPre);
        generateTransfer(dataMap, tableNameAfterPre);
        generateWeb(dataMap, tableNameAfterPre);
        generateApplication(dataMap, tableNameAfterPre);

        // 生成resource中的文件
        // generateResources(dataMap);

        System.out.println("backend generate finish");
    }

    private void generateAop(NeoMap dataMap) {
        // ControllerAop.java
        writeFile(dataMap, backendCodePath + "aop/ControllerAop.java", BACKEND_PRE + "controllerAop.ftl");

        // AutoCheck.java
        writeFile(dataMap, backendCodePath + "aop/AutoCheck.java", BACKEND_PRE + "autoCheck.ftl");
    }

    private void generateConfig(NeoMap dataMap) {
        // DbConfiguration.java
        writeFile(dataMap, backendCodePath + "config/DbConfiguration.java", BACKEND_PRE + "dbConfiguration.ftl");
    }

    private void generateDao(NeoMap dataMap, String tableNameAfterPre) {
        // XxxDao.java
        writeFile(dataMap, backendCodePath + "dao/" + getTablePathName(tableNameAfterPre) + "Dao.java", BACKEND_PRE + "dao.ftl");
    }

    private void generateException(NeoMap dataMap) {
        // BusinessException.java
        writeFile(dataMap, backendCodePath + "exception/BusinessException.java", BACKEND_PRE + "exception.ftl");
    }

    private void generateEntity() {
        // XxxDO
        EntityCodeGen codeGen = new EntityCodeGen()
            // 设置DB信息
            .setDb(dbUserName, dbUserPassword, dbUrl)
            // 设置项目路径
            .setProjectPath(projectModelPath)
            // 设置实体生成的包路径
            .setEntityPath(packagePath + ".entity")
            // 设置表前缀过滤
            .setPreFix(preFix)
            // 设置要排除的表
            // 设置只要的表
            .setIncludes(tableName)
            // 设置属性中数据库列名字向属性名字的转换，这里设置下划线，比如：data_user_base -> dataUserBase
            .setFieldNamingChg(NeoMap.NamingChg.UNDERLINE);

        // 代码生成
        codeGen.generate();
    }

    private void generateService(NeoMap dataMap, String tableNameAfterPre) {
        // XxxService.java
        writeFile(dataMap, backendCodePath + "service/" + getTablePathName(tableNameAfterPre) + "Service.java", BACKEND_PRE + "service.ftl");
    }

    private void generateTransfer(NeoMap dataMap, String tableNameAfterPre) {
        // XxxTransfer
        writeFile(dataMap, backendCodePath + "transfer/" + getTablePathName(tableNameAfterPre) + "Transfer.java", BACKEND_PRE + "transfer.ftl");
    }

    private void generateWeb(NeoMap dataMap, String tableNameAfterPre) {
        // baseResponseController
        writeBaseResponseController(dataMap);
        // XxxController
        writeFile(dataMap, backendCodePath + "web/controller/" + getTablePathName(tableNameAfterPre) + "Controller.java", BACKEND_PRE + "controller.ftl");

        // vo: req
        writeFile(dataMap, backendCodePath + "web/vo/req/" + getTablePathName(tableNameAfterPre) + "InsertReq.java", BACKEND_PRE + "insertReq.ftl");
        writeFile(dataMap, backendCodePath + "web/vo/req/" + getTablePathName(tableNameAfterPre) + "QueryReq.java", BACKEND_PRE + "queryReq.ftl");
        writeFile(dataMap, backendCodePath + "web/vo/req/" + getTablePathName(tableNameAfterPre) + "UpdateReq.java", BACKEND_PRE + "updateReq.ftl");

        // vo: rsp
        writeFile(dataMap, backendCodePath + "web/vo/rsp/" + getTablePathName(tableNameAfterPre) + "QueryRsp.java", BACKEND_PRE + "queryRsp.ftl");

        // vo: Pager.java
        writeFile(dataMap, backendCodePath + "web/vo/Pager.java", BACKEND_PRE + "pager.ftl");

        // vo: Response.java
        writeResponse(dataMap);
    }

    private void generateApplication(NeoMap dataMap, String tableNameAfterPre) {
        // XxxApplication.java
        writeFile(dataMap, backendCodePath + getTablePathName(tableNameAfterPre) + "Application.java", BACKEND_PRE + "applicationStart.ftl");
    }

    private void generateResources(NeoMap dataMap) {
        // application.yml
        writeFile(dataMap, backendResourcesPath + "/application.yml", BACKEND_PRE + "/resources/application.ftl");
        // application-local.yml
        writeFile(dataMap, backendResourcesPath + "/application-local.yml", BACKEND_PRE + "/resources/application-local.ftl");
        // application-dev.yml
        writeFile(dataMap, backendResourcesPath + "/application-dev.yml", BACKEND_PRE + "/resources/application-dev.ftl");
        // application-pre.yml
        writeFile(dataMap, backendResourcesPath + "/application-pre.yml", BACKEND_PRE + "/resources/application-pre.ftl");
        // application-pro.yml
        writeFile(dataMap, backendResourcesPath + "/application-pro.yml", BACKEND_PRE + "/resources/application-pro.ftl");
    }
}
