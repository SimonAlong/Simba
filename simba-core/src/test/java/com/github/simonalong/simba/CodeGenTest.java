package com.github.simonalong.simba;

import com.github.simonalong.simba.config.BackendConfig;
import com.github.simonalong.simba.config.CommonConfig;
import com.simonalong.neo.NeoMap;
import com.github.simonalong.simba.config.FrontConfig;
import com.github.simonalong.simba.config.TableConfig;
import com.github.simonalong.simba.xml.PomHandler;
import com.github.simonalong.simba.xml.element.DependencyElement;
import com.github.simonalong.simba.xml.element.ParentElement;
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
        commonConfig.setAppName("transformer_admin");
        commonConfig.setAppNameCn("脚手架平台");

        // 设置数据库信息
        commonConfig.setDbUrl("jdbc:mysql://localhost:3306/transformer");
        commonConfig.setDbUserName("neo_test");
        commonConfig.setDbUserPassword("neo@Test123");

        // 后端端口号：本地直连设置的后端端口号
        commonConfig.setBackendPort("8074");
        generator.addConfig(commonConfig);

        /*================================ 展示的表配置 ================================*/
        TableConfig tableConfig = new TableConfig();
        // 设置表前缀过滤
        tableConfig.setPreFix("trans_");
        // 设置要输出的表
        tableConfig.setTableName("trans_project_maven_rel");
        // 设置表的中文名，如果没有设置，则采用DB中的描述
        tableConfig.setTableDesc("项目拥有的maven列表");
        String column = "app_id";

//  create table `trans_table_config` (
        //  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
        //  `table_name` varchar (64) NOT NULL default '' COMMENT '表名',
        //  `table_create_sql` text NOT NULL COMMENT '表的创建语句',
        //  `table_pre` varchar (16) NOT NULL default '' COMMENT '前缀',
        //  `table_name_cn` varchar (64) NOT NULL default '' COMMENT '表名（中文）',
        //
        //  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        //  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
        //  PRIMARY KEY (`id`)
        //) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表的配置表';

        /*=========== 表的属性信息（非必填） =======*/
        // 设置表的字段和中文文案对应，不填则采用注释，如果注释没有则采用列名
        tableConfig.setFieldNameMap(NeoMap.of()
            .append("id", "主键id")
//            .append("name", "展示状态")
//            .append("table_name", "table_name")
        );



        // 设置："新增弹窗"中展示的属性，对应后端的InsertReq
        tableConfig.setInsertFields(column);

        // 设置："修改弹窗"中展示的属性，对应后端的UpdateReq
        tableConfig.setUpdateFields("id", column);

        // 设置："搜索框"中展示的属性，对应后端的Pager中的QueryReq
        tableConfig.setQueryFields(column);

        // 设置："表格"中展示的属性（必填）
        tableConfig.setTableShowFieldsMap(column);

        // 设置："表格扩展"中展示的属性（可不填）
        //tableConfig.setTableExpandFieldsMap("id", "update_time", "center", "status");

        generator.addConfig(tableConfig);

        /*================================ 前端配置 ================================*/
        FrontConfig frontConfig = new FrontConfig();
        // 设置前端代码路径
        frontConfig.setFrontCodePath("/Users/zhouzhenyong/private/Simba/Simba-robot-front");
        generator.addConfig(frontConfig);

        /*================================ 后端配置 ================================*/
        BackendConfig backendConfig = new BackendConfig();
        // 设置："后端项目模块路径"，（如果后端还有对应的组件包，则也要将组件包放到最后）
//        backendConfig.setBackendProjectPath("/Users/zhouzhenyong/tem/project/robot-integration");
        backendConfig.setBackendProjectPath("/Users/zhouzhenyong/private/Simba/isc-transformer/transformer-admin");
        // 设置：group
        backendConfig.setGroup("com.simonalong.xxxx");
        // 设置：artifact
        backendConfig.setArtifact("transformer-admin");
        // 设置：后端的名字
        backendConfig.setName("transformer-admin");
        // 设置：项目的描述
        backendConfig.setDescription("脚手架平台");
        // 设置："package包"
        backendConfig.setPackagePath("com.simonalong.xxxx.admin");
        generator.addConfig(backendConfig);

        generator.loadConfigContext();
//        generator.generateFront();
//        generator.generateBackend();

//        pomGenerate(backendConfig.getProjectPath());
    }

    public void pomGenerate(String projectPath) {
        PomHandler pomHandler = new PomHandler();

        // 配置Parent
        ParentElement parentElement = new ParentElement();
        parentElement.setGroupId("org.springframework.boot");
        parentElement.setArtifactId("spring-boot-starter-parent");
        parentElement.setVersion("2.0.4.RELEASE");

        // 配置项目
        pomHandler.setProjectPath(projectPath);
        pomHandler.setParentElement(parentElement);
        pomHandler.setGroupId("com.simonalong.demo");
        pomHandler.setArtifactId("robot-integration");
        pomHandler.setVersion("1.0.0-SNAPSHOT");
        pomHandler.setDescription("集成测试系统");

        // 添加依赖
        pomHandler.addDependency(DependencyElement.builder().groupId("org.codehaus.groovy").artifactId("groovy-all").version("3.0.3").build());

        pomHandler.generate();
    }

    @Test
    public void pomGenerateTest(){
        pomGenerate("/Users/zhouzhenyong/project/demo/isc-robot/robot-integration");
    }
}
