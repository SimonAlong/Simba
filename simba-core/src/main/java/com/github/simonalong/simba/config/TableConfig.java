package com.github.simonalong.simba.config;

import com.github.simonalong.simba.BiFunctional;
import com.github.simonalong.simba.entity.*;
import com.github.simonalong.simba.util.ListUtils;
import com.simonalong.neo.Neo;
import com.simonalong.neo.NeoMap;
import com.simonalong.neo.StringConverter;
import com.simonalong.neo.db.NeoColumn;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shizi
 * @since 2020/4/22 2:34 PM
 */
@Data
public class TableConfig implements ConfigContext {

    private static final String MYSQL_ENUM_TYPE = "ENUM";
    /**
     * mysql的时间字段
     */
    private static final List<String> MYSQL_TIME_TYPE = Arrays.asList("DATETIME", "TIMESTAMP", "DATE", "TIME", "YEAR");
    /**
     * 表名前缀
     */
    private String preFix;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表的描述
     */
    private String tableDesc;
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
     * 搜索框搜索的字段
     */
    private Map<String, FieldMeta> queryFieldsMap = new HashMap<>();
    /**
     * 界面上所有位置都不展示的字段
     */
    private Map<String, FieldMeta> excludesFieldsMap = new HashMap<>();
    /**
     * 表的属性为时间类型的字段
     */
    private Map<String, FieldMeta> tableTimeFieldMap = new HashMap<>();
    /**
     * "修改弹窗"（即编辑）中不可编辑的属性，针对{@code updateFieldMap}中展示的属性进行禁用
     * tableName, fieldName
     */
    private Map<String, FieldMeta> unEditFieldsMap = new HashMap<>();
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

    public void setQueryFields(String... fields) {
        generateMap(queryFieldsMap, fields);
    }

    public void setExcludesFields(String... fields) {
        generateMap(excludesFieldsMap, fields);
    }

    public void setUnEditFields(String... fields) {
        generateMap(unEditFieldsMap, fields);
    }

    public void setTableShowFieldsMap(String... fields) {
        generateMap(tableShowFieldsMap, fields);
    }

    public void setTableExpandFieldsMap(String... fields) {
        generateMap(tableShowExpandFieldsMap, fields);
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

    /**
     * 时间类型转换
     */
    private void fieldTypeChg(NeoColumn column, FieldInfo info) {
        String type = column.getJavaClass().getSimpleName();
        if ("BigInteger".equals(type)) {
            // 针对DB中的BigInteger类型，这里采用Long类型进行转换
            info.setFieldType("Long");
            return;
        } else {
            info.setFieldType(type);
        }

        if ("Timestamp".equals(type) || "Time".equals(type) || "Year".equals(type)) {
            // 针对DB中的时间类型，这里全部采用Data类型
            info.setFieldType("Date");
        } else {
            info.setFieldType(type);
        }
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
     * 判断字段是否为枚举类型
     */
    private boolean fieldIsEnum(List<NeoColumn> columns, String field) {
        if (null != columns && !columns.isEmpty()) {
            return columns.stream().anyMatch(c -> c.getColumnName().equals(field) && c.getColumnTypeName().equals(MYSQL_ENUM_TYPE));
        }
        return false;
    }

    private FieldInfo doConfigField(NeoColumn column, List<NeoColumn> columns, NeoMap importMap){
        String dbName = (column).getColumnName();
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
    }

    private void preGenerateImport(NeoMap importMap) {
        importMap.put("importDate", 0);
        importMap.put("importTime", 0);
        importMap.put("importTimestamp", 0);
        importMap.put("importBigDecimal", 0);
    }

    /**
     * 配置属性
     *
     * @param dataMap           结果接
     * @param columns           表的列
     * @param fieldsMap         待导入的数据值
     * @param filterColumnNames 不要的列名
     * @param function          执行配置的配置器
     * @param fieldKey          最后配置完之后对应的Map中的key
     */
    private void configField(NeoMap dataMap, List<NeoColumn> columns, Map<String, FieldMeta> fieldsMap, List<String> filterColumnNames,
        BiFunction<NeoColumn, List<NeoColumn>, Object> function, String fieldKey) {
        if (fieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }
        List<String> dbFieldList = fieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        excludeFieldList.addAll(filterColumnNames);
        List fieldInfos = columns.stream()
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .filter(column -> dbFieldList.contains(column.getColumnName()))
            .map(column -> function.apply(column, columns))
            .collect(Collectors.toList());
        dataMap.put(fieldKey, fieldInfos);
    }

    /**
     * 配置属性
     *
     * @param dataMap           结果接
     * @param columns           表的列
     * @param fieldsMap         待导入的数据值
     * @param function          执行配置的配置器
     * @param fieldKey          最后配置完之后对应的Map中的key
     * @param importKey         后端的import对应的key
     */
    private void configField(NeoMap dataMap, List<NeoColumn> columns, Map<String, FieldMeta> fieldsMap, BiFunctional<NeoColumn, List<NeoColumn>, NeoMap, Object> function, String fieldKey, String importKey) {
        if (null != fieldsMap && fieldsMap.isEmpty()) {
            return;
        }

        if (null == columns || columns.isEmpty()) {
            return;
        }
        NeoMap importMap = NeoMap.of();
        preGenerateImport(importMap);
        List<String> dbFieldList = null;
        if (null != fieldsMap) {
            dbFieldList = fieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        }

        List<String> excludeFieldList = excludesFieldsMap.values().stream().map(FieldMeta::getDbName).collect(Collectors.toList());
        List<String> finalDbFieldList = dbFieldList;
        List fieldInfos = columns.stream()
            .filter(column -> !excludeFieldList.contains(column.getColumnName()))
            .filter(column -> {
                if(null != finalDbFieldList) {
                    return finalDbFieldList.contains(column.getColumnName());
                }
                return true;
            })
            .map(column -> function.apply(column, columns, importMap))
            .collect(Collectors.toList());
        dataMap.put(fieldKey, fieldInfos);
        dataMap.put(importKey, importMap);
    }

    private void configInsertEntity(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, insertFieldsMap, (column, columnList, importMap)-> doConfigField(column, columns, importMap), "insertReqFields", "insertReqImport");
    }

    private void configUpdateEntity(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, updateFieldsMap, (column, columnList, importMap)-> doConfigField(column, columns, importMap), "updateReqFields", "updateReqImport");
    }

    private void configQueryReqEntity(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, queryFieldsMap, (column, columnList, importMap)-> doConfigField(column, columns, importMap), "queryReqFields", "queryReqImport");
    }

    private void configQueryRspEntity(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, null, (column, columnList, importMap)-> doConfigField(column, columns, importMap), "queryRspFields", "queryRspImport");
    }

    private void generateMap(Map<String, FieldMeta> dataMap, String... fields) {
        if (null == fields || fields.length == 0) {
            return;
        }

        Stream.of(fields).forEach(f -> dataMap.put(f, FieldMeta.of(f)));
    }

    /**
     * 设置表的属性和中文名的对应
     *
     * @param tableFieldMap fieldName-fieldDesc
     */
    public void setFieldNameMap(NeoMap tableFieldMap) {
        if (null == tableFieldMap || tableFieldMap.size() == 0) {
            return;
        }
        tableFieldMap.forEach((k, v) -> tableFieldNameMap.put(k, FieldInfo.of(k, (String) v)));
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
     * config_group -> config/group
     */
    private String getTableUrlName(String tableName) {
        return tableName.replaceAll("_", "/");
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
     * = 好前面的，逗号或者分号之间的字符
     */
    private String getKey(String value) {
        Integer endIndex = value.indexOf("=");
        return getKeyFromSplit(value, Arrays.asList(":", "：", ",", "，"), endIndex);
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

    private List<EnumMeta> getEnumValueList(String str, List<String> splitStrs) {
        for (String splitStr : splitStrs) {
            if (str.contains(splitStr)) {
                return getEnumValueListFromSemi(str, splitStr);
            }
        }
        return Collections.emptyList();
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

    /**
     * 添加枚举类型和对应的值
     */
    private void configEnumTypeField(NeoMap dataMap, List<NeoColumn> columns) {
        List<EnumInfo> infoList = new ArrayList<>();
        if (null != columns && !columns.isEmpty()) {
            columns.stream()
                .filter(c -> c.getColumnTypeName().equals(MYSQL_ENUM_TYPE))
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
                if (MYSQL_TIME_TYPE.contains(c.getColumnTypeName())) {
                    tableTimeFieldMap.computeIfAbsent(c.getColumnName(), FieldMeta::of);
                }
            });
        }
    }

    /**
     * 对于枚举类型，获取其中枚举类型的描述
     *
     * @param fieldDesc 性别用户的性别:MALE=男性;FEMALE=女性;UNKNOWN=未知
     * @return 性别用户的性别
     */
    private String getEnumDesc(String fieldDesc, List<String> splitStrs) {
        for (String splitStr : splitStrs) {
            if (fieldDesc.contains(splitStr)) {
                return Arrays.asList(fieldDesc.split(splitStr)).get(0);
            }
        }
        return fieldDesc;
    }

    private String getRemark(NeoColumn c) {
        String remarks = c.getInnerColumn().getRemarks();
        if (null != remarks) {
            if (c.getColumnTypeName().equals(MYSQL_ENUM_TYPE)) {
                return getEnumDesc(remarks, Arrays.asList(":", "：", ",", "，"));
            }
            return remarks;
        }
        return c.getColumnName();
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

    private void doUpdateInsert(UpdateInsertFieldInfo info, String dbFieldName, NeoColumn column){
        // 设置时间类型
        if (fieldIsTimeField(dbFieldName)) {
            info.getFieldInfo().setTimeFlag(1);
        }

        // 设置枚举类型
        if (column.getColumnTypeName().equals(MYSQL_ENUM_TYPE)) {
            info.getFieldInfo().setEnumFlag(1);
        }
    }

    /**
     * 配置新增弹窗要展示的字段
     * <p>
     * 注意：如果有这么几个基本字段则这里默认在添加框中不展示
     */
    private void configInsertField(NeoMap dataMap, List<NeoColumn> columns) {
        List<String> columnsList = new ArrayList<>();
        columnsList.add("id");
        columnsList.add("create_time");
        columnsList.add("update_time");
        configField(dataMap, columns, insertFieldsMap, columnsList, (column, columnList)->{
            String dbName = column.getColumnName();
            UpdateInsertFieldInfo info = UpdateInsertFieldInfo.of((dbName), getFieldDesc(dbName, column.getInnerColumn().getRemarks()));
            doUpdateInsert(info, dbName, column);
            return info;
        }, "insertFields");
    }

    private Boolean fieldIsUnEdit(String fieldDbName) {
        if (null != unEditFieldsMap) {
            return unEditFieldsMap.containsKey(fieldDbName);
        }
        return true;
    }

    /**
     * 设置哪些字段是可以更新的，首先过滤排除表，然后查看展示表
     */
    private void configUpdateField(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, updateFieldsMap, new ArrayList<>(), (column, columnList)->{
            String dbName = column.getColumnName();
            UpdateInsertFieldInfo info = UpdateInsertFieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks())).setCanEdit(1);

            // 设置哪些字段是只可查看不可编辑
            if (fieldIsUnEdit(dbName)) {
                info.setCanEdit(0);
            }

            doUpdateInsert(info, dbName, column);
            return info;
        }, "updateFields");
    }

    private void doField(FieldInfo info, String dbName, List<NeoColumn> columns){
        // 时间戳设置
        if (fieldIsTimeField(info.getDbName())) {
            info.setTimeFlag(1);
        }

        // 枚举类型设置
        if (fieldIsEnum(columns, dbName)) {
            info.setEnumFlag(1);
        }
    }

    private void configSearchField(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, queryFieldsMap, new ArrayList<>(), (column, columnList)->{
            String dbName = column.getColumnName();
            FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));
            doField(info, dbName, columnList);
            return info;
        }, "searchFields");
    }

    private void configTableShowField(NeoMap dataMap, List<NeoColumn> columns) {
        configField(dataMap, columns, tableShowFieldsMap, new ArrayList<>(), (column, columnList)->{
            String dbName = column.getColumnName();
            FieldInfo info = FieldInfo.of(dbName, getFieldDesc(dbName, column.getInnerColumn().getRemarks()));
            doField(info, dbName, columnList);
            return info;
        }, "tableShowFields");
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
                doField(info, dbName, columns);
                return info;
            })
            .collect(Collectors.toList());

        dataMap.put("expandExist", 1);
        dataMap.put("expandFields", ListUtils.split(fieldInfoList, 4));
    }

    @Override
    public void visit(NeoMap dataMap) {
        if (null == dataMap) {
            return;
        }
        dataMap.put("tableName", tableName);
        dataMap.put("tableDesc", tableDesc);
        dataMap.put("preFix", preFix);
        String tableNameAfterPre = excludePreFix();
        dataMap.put("tableNameAfterPre", tableNameAfterPre);
        dataMap.put("tablePathName", StringConverter.underLineToBigCamel(tableNameAfterPre));
        dataMap.put("tableUrlName", getTableUrlName(tableNameAfterPre));
        dataMap.put("tablePathNameLower", StringConverter.underLineToSmallCamel(tableNameAfterPre));
        dataMap.put("tableComponentInfos", NeoMap.of()
            .append("tableName", StringConverter.underLineToSmallCamel(tableNameAfterPre))
            .append("tablePathName", StringConverter.underLineToBigCamel(tableNameAfterPre)));
        dataMap.put("tableInfo", TableInfo.of(StringConverter.underLineToSmallCamel(excludePreFix()), tableDesc));

        dataMap.put("tableNameCn", tableName);
        if (null != tableDesc) {
            dataMap.put("tableNameCn", tableDesc);
        }

        if (dataMap.containsKey("db")) {
            Neo neo = dataMap.get(Neo.class, "db");
            List<NeoColumn> columns = neo.getColumnList(dataMap.getString("tableName"));
            configEnumTypeField(dataMap, columns);
            configTimeField(columns);
            configFieldName(columns);

            /*========================= 前端的配置读取 =========================*/
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

            /*========================= 后端的配置读取 =========================*/
            configInsertEntity(dataMap, columns);
            configUpdateEntity(dataMap, columns);
            configQueryReqEntity(dataMap, columns);
            configQueryRspEntity(dataMap, columns);
        }

    }
}
