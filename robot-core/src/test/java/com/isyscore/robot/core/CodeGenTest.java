package com.isyscore.robot.core;

import com.isyscore.isc.neo.NeoMap;
import com.isyscore.robot.core.config.BackendConfig;
import com.isyscore.robot.core.config.CommonConfig;
import com.isyscore.robot.core.config.FrontConfig;
import com.isyscore.robot.core.config.TableConfig;
import com.isyscore.robot.core.xml.PomHandler;
import com.isyscore.robot.core.xml.element.DependencyElement;
import com.isyscore.robot.core.xml.element.ParentElement;
import org.junit.Test;

/**
 * @author shizi
 * @since 2019/12/3 11:53 上午
 */
public class CodeGenTest {

    @Test
    public void codeGenerateTest() {
        CodeGenerator generator = new CodeGenerator();

        /*================================ 公共配置 ================================*/
        CommonConfig commonConfig = new CommonConfig();
        // 设置应用名字
        commonConfig.setAppName("robot");
        commonConfig.setAppNameCn("集成测试系统");

        // 设置数据库信息
        commonConfig.setDbUrl("jdbc:mysql://localhost:3306/neo");
        commonConfig.setDbUserName("neo_test");
        commonConfig.setDbUserPassword("neo@Test123");

        // 后端端口号：本地直连设置的后端端口号
        commonConfig.setBackendPort("8074");
        generator.addConfig(commonConfig);

        /*================================ 展示的表配置 ================================*/
        TableConfig tableConfig = new TableConfig();
        // 设置表前缀过滤
        tableConfig.setPreFix("neo_");
        // 设置要输出的表
        tableConfig.setTableName("neo_city");
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        tableConfig.setTableDesc("城市表");

        /*=========== 表的属性信息（非必填） =======*/
        // 设置表的字段和中文文案对应，不填则采用注释，如果注释没有则采用列名
        tableConfig.setFieldNameMap(NeoMap.of()
            .append("id", "主键id")
            .append("province_code", "省份编码")
            .append("city_code", "市编码")
            .append("name", "名称")
            .append("create_time", "创建时间")
            .append("update_time", "更新时间")
            .append("center", "中心点经纬度")
            .append("status", "状态：1新地址，0老地址"));

        // 设置："新增弹窗"中展示的属性，对应后端的InsertReq
        tableConfig.setInsertFields("province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："修改弹窗"中展示的属性，对应后端的UpdateReq
        tableConfig.setUpdateFields("id", "province_code", "city_code", "name", "create_time", "update_time", "center", "status");

        // 设置："搜索框"中展示的属性，对应后端的Pager中的QueryReq
        tableConfig.setQueryFields("province_code", "city_code", "name");

        // 设置："不展示属性"，一旦设置界面上任何位置都不会展示（可不填）
        tableConfig.setExcludesFields("status");

        // 设置："修改弹窗"中展示但是不可编辑的属性，基于公共 setUpdateFieldsMap 中展示的属性进行禁用
        tableConfig.setUnEditFields("id", "name");

        // 设置："表格"中展示的属性（必填）
        tableConfig.setTableShowFieldsMap("province_code", "city_code", "name", "create_time");

        // 设置："表格扩展"中展示的属性（可不填）
        tableConfig.setTableExpandFieldsMap("id", "update_time", "center", "status");

        generator.addConfig(tableConfig);

        /*================================ 前端配置 ================================*/
        FrontConfig frontConfig = new FrontConfig();
        // 设置前端代码路径
        frontConfig.setFrontCodePath("/Users/zhouzhenyong/project/isyscore/isc-robot-front");
//        generator.addConfig(frontConfig);

        /*================================ 后端配置 ================================*/
        BackendConfig backendConfig = new BackendConfig();
        // 设置："后端项目模块路径"，（如果后端还有对应的组件包，则也要将组件包放到最后）
        backendConfig.setBackendProjectPath("/Users/zhouzhenyong/project/isyscore/isc-robot/robot-integration");
        // 设置：group
        backendConfig.setGroup("com.isyscore.robot");
        // 设置：artifact
        backendConfig.setArtifact("robot-integration");
        // 设置：后端的名字
        backendConfig.setName("robot");
        // 设置：项目的描述
        backendConfig.setDescription("脚手架继承测试项目");
        // 设置："package包"
        backendConfig.setPackagePath("com.isyscore.robot.integration");
        generator.addConfig(backendConfig);

        generator.loadConfigContext();
//        generator.generateFront();
        generator.generateBackend();
    }

    @Test
    public void pomGenerateTest() {
        PomHandler pomHandler = new PomHandler();

        ParentElement parentElement = new ParentElement();
        parentElement.setGroupId("org.springframework.boot");
        parentElement.setArtifactId("spring-boot-starter-parent");
        parentElement.setGroupId("2.0.4.RELEASE");

        DependencyElement neoDependency = new DependencyElement();
        neoDependency.setGroupId("com.simonalong");
        neoDependency.setArtifactId("neo");
        neoDependency.setVersion("1.0.0-SNAPSHOT");


        DependencyElement lombokDependency = new DependencyElement();
        lombokDependency.setGroupId("org.projectlombok");
        lombokDependency.setArtifactId("lombok");

        // 配置Parent
        pomHandler.setProjectPath("/Users/zhouzhenyong/project/isyscore/isc-robot/robot-integration");
        pomHandler.setParentElement(parentElement);
        pomHandler.setGroupId("com.isyscore.isc");
        pomHandler.setArtifactId("robot");
        pomHandler.setVersion("1.0.0-SNAPSHOT");
        pomHandler.setDescription("集成测试系统");

        // 添加依赖
        pomHandler.addDependency(neoDependency);
        pomHandler.addDependency(lombokDependency);

        pomHandler.generate();
    }
}
