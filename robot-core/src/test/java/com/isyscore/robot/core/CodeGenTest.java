package com.isyscore.robot.core;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.codegen.EntityCodeGen;
import com.isyscore.robot.core.util.Maps;
import org.junit.Test;

/**
 * @author shizi
 * @since 2019/12/3 11:53 上午
 */
public class CodeGenTest {

    @Test
    @SuppressWarnings("all")
    public void genFront() {
        CodeGen codeGen = new CodeGen();

        // 设置应用名字
        codeGen.setAppName("sequence");

        /*==================== DB配置（必填） =======================*/
        // 设置数据库信息
        codeGen.setDbUrl("jdbc:mysql://localhost:3306/neo");
        codeGen.setDbUserName("neo_test");
        codeGen.setDbUserPassword("neo@Test123");

        /*========================================================================= 前端代码配置 =================*/
        // 设置前端代码路径
        codeGen.setFrontCodePath("/Users/simon/work/project/portal-front");

        /*==================== 后端代理配置（非必填） ===================*/
        // 本地直连：直连模式，则会直接修改为前端直连后端Host
         codeGen.setDirect(true);
        // 后端端口号：本地直连设置的后端端口号
         codeGen.setBackendPort("8084");

        /*================= 要展示的表基本信息（必填） =================*/
        // 设置表前缀过滤
         codeGen.setPreFix("neo_");
        // 设置要输出的表
        codeGen.setTableName("neo_city");
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        codeGen.setTableDesc("城市表");

        /*================== 表的属性信息（非必填） ===================*/
        // 设置表的字段和中文文案对应
        codeGen.setFieldNameMap(Maps.of()
            .add("id", "'主键id'")
            .add("province_code", "'省份编码'")
            .add("city_code", "'市编码'")
            .add("name", "'名称'")
            .add("create_time", "'创建时间'")
            .add("update_time", "'更新时间'")
            .add("center", "'中心点经纬度'")
            .add("status", "状态：1新地址，0老地址")
            .build());

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        //codeGen.setExcludesFields("auth_user_name");
        /*================ "新增和编辑弹窗"显示字段（必填） =============*/
        // 设置："新增弹窗"中展示的属性
        codeGen.setInsertFields("province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："修改弹窗"中展示的属性
        codeGen.setUpdateFields("id", "province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于上面 setUpdateFieldsMap 中展示的属性进行禁用
        codeGen.setUnEditFields("id", "name");

        /*======================== 搜索展示属性 =======================*/
        // 设置："搜索框"中展示的属性（必填）
        codeGen.setQueryFields("province_code", "city_code", "name");

        // 设置："表格"中展示的属性（必填）
        codeGen.setTableShowFieldsMap("province_code", "city_code", "name", "create_time");

        // 设置："表格扩展"中展示的属性（可不填）
        codeGen.setTableExpandFieldsMap("id", "update_time", "center", "status");

        /*============================================================================ 后端代码生成器（非必填） ===================*/
        // 设置："后端项目模块路径"，（如果后端还有对应的组件包，则也要将组件包放到最后）
        codeGen.setBackendModulePath("/Users/zhouzhenyong/project/isyscore/ibo-robot/robot-integration");

        // 设置："package包"
        codeGen.setBackendPackage("com.isyscore.robot.integration");

        /*=========================== 生成代码 ========================*/
        // 生成前端
        // codeGen.generateFront();

        // 生成后端
        codeGen.generateBackend();
    }
}
