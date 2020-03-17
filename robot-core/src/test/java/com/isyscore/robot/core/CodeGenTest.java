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

        // 设置前端代码路径
        codeGen.setFrontCodePath("/Users/simon/work/project/portal-front");
        // 设置应用名字
        codeGen.setAppName("sequence");

        /*================================ 后端代理配置（非必填） ================================*/
        // 本地直连：直连模式，则会直接修改为前端直连后端Host
        // codeGen.setDirect(true);
        // 后端端口号：本地直连设置的后端端口号
        // codeGen.setBackendPort("8084");

        /*================================ DB配置（必填） ================================*/
        // 设置数据库信息
        codeGen.setDbUrl("jdbc:mysql://xxxx:3306/sequence");
        codeGen.setDbUserName("xxx_rw");
        codeGen.setDbUserPassword("xxxxxxxxx");

        /*================================ 要展示的表基本信息（必填） ================================*/
        // 设置表前缀过滤
        // codeGen.setPreFix("portal_");
        // 设置要输出的表
        codeGen.setTableName("snowflake_namespace");
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        codeGen.setTableDesc("命名空间");

        /*================================ 表的属性信息（非必填） ================================*/
        // 设置表的字段和中文文案对应
        codeGen.setFieldNameMap(Maps.of()
            .add("namespace", "命名空间")
            .add("desc", "描述")
            .add("mode", "模式")
            .add("create_time", "创建时间")
            .add("update_time", "更新时间")
            .add("create_user_name", "创建者")
            .add("update_user_name", "更新者")
            .build());

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        //codeGen.setExcludesFields("auth_user_name");
        /*================================ "新增和编辑弹窗"显示字段（必填） ================================*/
        // 设置："新增弹窗"中展示的属性
        codeGen.setInsertFields("namespace", "mode", "desc");

        // 设置："修改弹窗"中展示的属性
        codeGen.setUpdateFields("namespace", "mode", "desc");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于上面 setUpdateFieldsMap 中展示的属性进行禁用
        codeGen.setUnEditFields("namespace", "mode");

        /*================================ 搜索展示属性 ================================*/
        // 设置："搜索框"中展示的属性（必填）
        codeGen.setQueryFields("namespace", "mode");

        // 设置："表格"中展示的属性（必填）
        codeGen.setTableShowFieldsMap("namespace", "mode", "desc", "auth_user_name", "create_time");

        // 设置："表格扩展"中展示的属性（可不填）
        codeGen.setTableExpandFieldsMap("create_time", "create_user_name", "update_time", "update_user_name");

        /*================================ 后端代码生成器（非必填） ================================*/
        // 设置："后端项目模块路径"，后端项目的路径（如果不设置，则后端代码不会生成）
        //codeGen.setBackendModulePath("/Users/simon/work/project/portal/portal-server");

        // 设置："package包"
        //codeGen.setBackendPackage("com.ggj.platform.portal.server");

        /*================================ 生成代码 ================================*/
        // 生成前端
        codeGen.generateFront();

        // 生成后端
        //codeGen.generateBackend();
    }
}
