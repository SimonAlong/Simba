package com.isyscore.robot.core;

import com.isyscore.isc.neo.NeoMap;
import org.junit.Test;

/**
 * @author shizi
 * @since 2019/12/3 11:53 上午
 */
public class CodeGenTest {

    @Test
    @SuppressWarnings("all")
    public void generate1() {
        CodeGen codeGen = new CodeGen();

        /*============================================================================ 公共配置（必填） =======================*/
        // 设置应用名字
        codeGen.setAppName("robot");
        codeGen.setAppNameCn("集成测试系统");
        // 设置数据库信息
        codeGen.setDbUrl("jdbc:mysql://localhost:3306/neo");
        codeGen.setDbUserName("neo_test");
        codeGen.setDbUserPassword("neo@Test123");

        // 后端端口号：本地直连设置的后端端口号
        codeGen.setBackendPort("8084");

        // 设置表前缀过滤
        codeGen.setPreFix("neo_");
        // 设置要输出的表
        codeGen.setTableName("neo_city");

        // 设置："新增弹窗"中展示的属性，对应后端的InsertReq
        codeGen.setInsertFields("province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："修改弹窗"中展示的属性，对应后端的UpdateReq
        codeGen.setUpdateFields("id", "province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："搜索框"中展示的属性，对应后端的Pager中的QueryReq
        codeGen.setQueryFields("province_code", "city_code", "name");

        /*=============================================================================== 前端代码配置 =======================*/
        // 设置前端代码路径
        codeGen.setFrontCodePath("/Users/zhouzhenyong/project/isyscore/isc-front-base");

        /*========= 要展示的表基本信息（必填） ========*/
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        codeGen.setTableDesc("城市表");

        /*=========== 表的属性信息（非必填） =======*/
        // 设置表的字段和中文文案对应，不填则采用注释，如果注释没有则采用列名
        codeGen.setFieldNameMap(NeoMap.of()
            .append("id", "主键id")
            .append("province_code", "省份编码")
            .append("city_code", "市编码")
            .append("name", "名称")
            .append("create_time", "创建时间")
            .append("update_time", "更新时间")
            .append("center", "中心点经纬度")
            .append("status", "状态：1新地址，0老地址"));

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        codeGen.setExcludesFields("auth_user_name");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于公共 setUpdateFieldsMap 中展示的属性进行禁用
        codeGen.setUnEditFields("id", "name");

        /*============= 界面表格展示信息 ===========*/
        // 设置："表格"中展示的属性（必填）
        codeGen.setTableShowFieldsMap("province_code", "city_code", "name", "create_time");

        // 设置："表格扩展"中展示的属性（可不填）
        codeGen.setTableExpandFieldsMap("id", "update_time", "center", "status");

        /*============================================================================== 后端代码生成器（非必填） ===================*/
        // 设置："后端项目模块路径"，（如果后端还有对应的组件包，则也要将组件包放到最后）
        codeGen.setBackendModulePath("/Users/zhouzhenyong/project/isyscore/isc-robot/robot-integration");

        // 设置："package包"
        codeGen.setBackendPackage("com.isyscore.robot.integration");

        /*============================================================================== 生成代码 ================================*/
        // 生成前端
        // codeGen.generateFront();

        // 生成后端
        codeGen.generateBackend();
    }

    @Test
    public void generate2(){
        CodeGen codeGen = new CodeGen();

        /*============================================================================ 公共配置（必填） =======================*/
        // 设置应用名字
        codeGen.setAppName("robot");
        // 设置数据库信息
        codeGen.setDbUrl("jdbc:mysql://localhost:3306/neo");
        codeGen.setDbUserName("neo_test");
        codeGen.setDbUserPassword("neo@Test123");

        // 后端端口号：本地直连设置的后端端口号
        codeGen.setBackendPort("8084");

        // 设置表前缀过滤
        codeGen.setPreFix("ibo_");
        // 设置要输出的表
        codeGen.setTableName("ibo_business_city");

        // 设置："新增弹窗"中展示的属性，对应后端的InsertReq
        codeGen.setInsertFields("province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："修改弹窗"中展示的属性，对应后端的UpdateReq
        codeGen.setUpdateFields("id", "province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："搜索框"中展示的属性，对应后端的Pager中的QueryReq
        codeGen.setQueryFields("province_code", "city_code", "name");

        /*=============================================================================== 前端代码配置 =======================*/
        // 设置前端代码路径
        codeGen.setFrontCodePath("/Users/zhouzhenyong/project/isyscore/isc-front-base");

        /*========= 要展示的表基本信息（必填） ========*/
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        codeGen.setTableDesc("业务城市表");

        /*=========== 表的属性信息（非必填） =======*/
        // 设置表的字段和中文文案对应，不填则采用注释，如果注释没有则采用列名
        codeGen.setFieldNameMap(NeoMap.of()
            .append("id", "主键id")
            .append("province_code", "省份编码")
            .append("city_code", "市编码")
            .append("name", "名称")
            .append("create_time", "创建时间")
            .append("update_time", "更新时间")
            .append("center", "中心点经纬度")
            .append("status", "状态：1新地址，0老地址"));

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        codeGen.setExcludesFields("status");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于公共 setUpdateFieldsMap 中展示的属性进行禁用
        codeGen.setUnEditFields("id", "name");

        /*============= 界面表格展示信息 ===========*/
        // 设置："表格"中展示的属性（必填）
        codeGen.setTableShowFieldsMap("province_code", "city_code", "name", "create_time");

        // 设置："表格扩展"中展示的属性（可不填）
        codeGen.setTableExpandFieldsMap("id", "update_time", "center", "status");

        /*============================================================================== 后端代码生成器（非必填） ===================*/
        // 设置："后端项目模块路径"，（如果后端还有对应的组件包，则也要将组件包放到最后）
        codeGen.setBackendModulePath("/Users/zhouzhenyong/project/isyscore/isc-robot/robot-integration");

        // 设置："package包"
        codeGen.setBackendPackage("com.isyscore.robot.integration");

        /*============================================================================== 生成代码 ================================*/
        // 生成前端
        codeGen.generateFront();

        // 生成后端
        codeGen.generateBackend();
    }
}
