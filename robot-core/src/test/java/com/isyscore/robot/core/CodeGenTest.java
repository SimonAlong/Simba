package com.isyscore.robot.core;

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

        /*========================================================================= 前端代码配置 =================*/
        // 设置前端代码路径
        codeGen.setFrontCodePath("/Users/simon/work/project/portal-front");

        /*==================== 后端代理配置（非必填） ===================*/
        // 本地直连：直连模式，则会直接修改为前端直连后端Host
         codeGen.setDirect(true);
        // 后端端口号：本地直连设置的后端端口号
         codeGen.setBackendPort("8084");

        /*==================== DB配置（必填） =======================*/
        // 设置数据库信息
        codeGen.setDbUrl("jdbc:mysql://localhost:3306/neo");
        codeGen.setDbUserName("neo_test");
        codeGen.setDbUserPassword("neo@Test123");

        /*================= 要展示的表基本信息（必填） =================*/
        // 设置表前缀过滤
         codeGen.setPreFix("neo_");
        // 设置要输出的表
        codeGen.setTableName("neo_table4");
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        codeGen.setTableDesc("测试表");

        /*================== 表的属性信息（非必填） ===================*/
        // 设置表的字段和中文文案对应
        codeGen.setFieldNameMap(Maps.of()
            .add("id", "命名空间")
            .add("group", "分组")
            .add("name", "名字")
            .add("user_name", "用户名")
            .add("age", "年龄")
            .add("n_id", "n_id")
            .add("sort", "排序")
            .add("enum1", "枚举1")
            .add("time", "time时间")
            .add("year", "年")
            .add("date", "data时间")
            .add("datetime", "datetime时间")
            .build());

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        //codeGen.setExcludesFields("auth_user_name");
        /*================ "新增和编辑弹窗"显示字段（必填） =============*/
        // 设置："新增弹窗"中展示的属性
        codeGen.setInsertFields("group", "name", "user_name", "age", "sort", "enum1");

        // 设置："修改弹窗"中展示的属性
        codeGen.setUpdateFields("group", "name", "user_name", "age", "sort", "enum1");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于上面 setUpdateFieldsMap 中展示的属性进行禁用
        codeGen.setUnEditFields("group", "name");

        /*======================== 搜索展示属性 =======================*/
        // 设置："搜索框"中展示的属性（必填）
        codeGen.setQueryFields("group", "name");

        // 设置："表格"中展示的属性（必填）
        codeGen.setTableShowFieldsMap("group", "name", "user_name", "age", "sort", "enum1");

        // 设置："表格扩展"中展示的属性（可不填）
        codeGen.setTableExpandFieldsMap("time", "year", "date", "datetime");

        /*============================================================================ 后端代码生成器（非必填） ===================*/
        // 设置："后端项目模块路径"，后端项目的路径（如果不设置，则后端代码不会生成）
        codeGen.setBackendModulePath("/Users/zhouzhenyong/project/isyscore/ibo-robot");

        // 设置："package包"
        codeGen.setBackendPackage("com.isyscore.robot.integration");

        /*=========================== 生成代码 ========================*/
        // 生成前端
//        codeGen.generateFront();

        // 生成后端
        codeGen.generateBackend();
    }
}
