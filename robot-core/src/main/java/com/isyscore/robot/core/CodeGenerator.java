package com.isyscore.robot.core;

import com.isyscore.isc.neo.NeoMap;
import com.isyscore.isc.neo.StringConverter;
import com.isyscore.isc.neo.codegen.EntityCodeGen;
import com.isyscore.robot.core.config.*;
import com.isyscore.robot.core.entity.TableInfo;
import com.isyscore.robot.core.util.FileUtil;
import com.isyscore.robot.core.util.FreeMarkerTemplateUtil;
import freemarker.template.TemplateException;
import lombok.Data;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author shizi
 * @since 2020/4/22 2:58 PM
 */
@Data
public class CodeGenerator {

    private static final String MYSQL_ENUM_TYPE = "ENUM";
    private static final String FRONT_PRE = "front/";
    private static final String BACKEND_RS_PATH = "backend/";
    private List<ConfigContext> baseConfigList = new ArrayList<>();
    private ConfigLoader configLoader = new ConfigLoader();

    /**
     * 载入配置上下文
     */
    public void loadConfigContext() {
        configLoader.load(baseConfigList);
    }

    public void addConfig(ConfigContext configContext) {
        baseConfigList.add(configContext);
    }

    private void writeFile(NeoMap dataMap, String filePath, String templateName) {
        try {
            if (!FileUtil.exist(filePath)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileUtil.getFile(filePath)));
                Objects.requireNonNull(FreeMarkerTemplateUtil.getTemplate(templateName)).process(dataMap, bufferedWriter);
                System.out.println("file generate finish: " + filePath);
            }
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileWithCover(NeoMap dataMap, String filePath, String templateName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileUtil.getFile(filePath)));
            Objects.requireNonNull(FreeMarkerTemplateUtil.getTemplate(templateName)).process(dataMap, bufferedWriter);
            System.out.println("file generate finish: " + filePath);
        } catch (TemplateException | IOException e) {
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
                .append("        // authority: ['")
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
    @SuppressWarnings("unchecked")
    private void writeRouterConfig(NeoMap dataMap, String filePath) {
        try {
            String oldRouterConfigText = FileUtil.read(filePath);
            String dbName = String.valueOf(dataMap.get("appName"));
            Map<String, String> componentInfoMap = (Map<String, String>) dataMap.get("tableComponentInfos");

            if (null != componentInfoMap && !componentInfoMap.isEmpty()) {
                Triple tripleList = new MutableTriple<>(dbName, componentInfoMap.get("tableName"), componentInfoMap.get("tablePathName"));

                // 获取表名和表中文名对应的map
                Map<String, String> tableNameDescMap = new HashMap<>();
                tableNameDescMap.put(dataMap.getString("tableName"), dataMap.getString("tableDesc"));

                FileUtil.write(filePath, addConfigRouter(tableNameDescMap, oldRouterConfigText, tripleList));
            }
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

    private void generateFront(NeoMap dataMap) {
        String tableNameAfterPre = dataMap.getString("tableNameAfterPre");

        if (dataMap.containsKey("frontCodePath")) {
            String frontCodePath = dataMap.getString("frontCodePath");
            String appName = dataMap.getString("appName");

            // router.config.js
            writeRouterConfig(dataMap, frontCodePath + "/config/router.config.js");

            // menu.js
            writeMenu(dataMap, frontCodePath + "/src/locales/zh-CN/menu.js");

            // List
            writeFile(dataMap, frontCodePath + "/src/pages/" + appName + "/" + StringConverter.underLineToBigCamel(tableNameAfterPre) + "List.js", FRONT_PRE + "tableList.ftl");
            writeFile(dataMap, frontCodePath + "/src/pages/" + appName + "/" + StringConverter.underLineToBigCamel(tableNameAfterPre) + "List.less", FRONT_PRE + "tableList.less");
            // model
            writeFile(dataMap, frontCodePath + "/src/models/" + appName + "/" + StringConverter.underLineToSmallCamel(tableNameAfterPre) + "Model.js", FRONT_PRE + "tableModel.ftl");
            // api
            writeFile(dataMap, frontCodePath + "/src/services/" + appName + "/" + StringConverter.underLineToSmallCamel(tableNameAfterPre) + "Api.js", FRONT_PRE + "tableApi.ftl");

            // config.js
            writeFileWithCover(dataMap, frontCodePath + "/config/config.js", FRONT_PRE + "frontConfig.ftl");
        }
    }

    private void generateAop(NeoMap dataMap) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // AutoCheck.java
        writeFile(dataMap, backendCodePath + "aop/AutoCheck.java", BACKEND_RS_PATH + "aop/AutoCheck.ftl");

        // ControllerAop.java
        writeFile(dataMap, backendCodePath + "aop/ControllerAop.java", BACKEND_RS_PATH + "aop/ControllerAop.ftl");
    }

    private void generateConfig(NeoMap dataMap) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // DbConfiguration.java
        writeFile(dataMap, backendCodePath + "config/DbConfiguration.java", BACKEND_RS_PATH + "config/DbConfiguration.ftl");
    }

    private void generateConstant(NeoMap dataMap) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // AppConstant.java
        writeFile(dataMap, backendCodePath + "constant/AppConstant.java", BACKEND_RS_PATH + "constant/AppConstant.ftl");
    }

    private void generateDao(NeoMap dataMap, String tableNameAfterPre) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // XxxDao.java
        writeFile(dataMap, backendCodePath + "dao/" + StringConverter.underLineToBigCamel(tableNameAfterPre) + "Dao.java", BACKEND_RS_PATH + "dao/XxDao.ftl");
    }

    private void generateException(NeoMap dataMap) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // BusinessException.java
        writeFile(dataMap, backendCodePath + "exception/BusinessException.java", BACKEND_RS_PATH + "exception/BusinessException.ftl");
    }

    private void generateEntity(NeoMap dataMap) {
        String dbUserName = dataMap.getString("dbUserName");
        String dbUserPassword = dataMap.getString("dbUserPassword");
        String dbUrl = dataMap.getString("dbUrl");

        String projectPath = dataMap.getString("projectPath");
        String packagePath = dataMap.getString("packagePath");
        String preFix = dataMap.getString("preFix");
        String tableName = dataMap.getString("tableName");

        // XxxDO
        EntityCodeGen codeGen = new EntityCodeGen()
            // 设置DB信息
            .setDb(dbUserName, dbUserPassword, dbUrl)
            // 设置项目路径
            .setProjectPath(projectPath)
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
        String backendCodePath = dataMap.getString("backendCodePath");
        String tablePathName = StringConverter.underLineToBigCamel(tableNameAfterPre);
        // XxService.java
        writeFile(dataMap, backendCodePath + "service/" + tablePathName + "Service.java", BACKEND_RS_PATH + "service/XxService.ftl");

        // auth
        // AuthService.ftl
        writeFile(dataMap, backendCodePath + "service/auth/AuthService.java", BACKEND_RS_PATH + "service/auth/AuthService.ftl");
        // MenuAuthHandler.ftl
        writeFile(dataMap, backendCodePath + "service/auth/MenuAuthHandler.java", BACKEND_RS_PATH + "service/auth/MenuAuthHandler.ftl");
        // XxMenuAuthHandler.ftl
        writeFile(dataMap, backendCodePath + "service/auth/" + tablePathName + "MenuAuthHandler.java", BACKEND_RS_PATH + "service/auth/XxMenuAuthHandler.ftl");
    }

    private void generateTransfer(NeoMap dataMap, String tableNameAfterPre) {
        String backendCodePath = dataMap.getString("backendCodePath");
        // XxxTransfer
        writeFile(dataMap, backendCodePath + "transfer/" + StringConverter.underLineToBigCamel(tableNameAfterPre) + "Transfer.java", BACKEND_RS_PATH + "transfer/Transfer.ftl");
    }

    private void generateWeb(NeoMap dataMap, String tableNameAfterPre) {
        String backendCodePath = dataMap.getString("backendCodePath");
        String tablePathName = StringConverter.underLineToBigCamel(tableNameAfterPre);
        // BaseResponseController
        writeFile(dataMap, backendCodePath + "web/controller/BaseResponseController.java", BACKEND_RS_PATH + "web/controller/BaseResponseController.ftl");
        // XxxController
        writeFile(dataMap, backendCodePath + "web/controller/" + tablePathName + "Controller.java", BACKEND_RS_PATH + "web/controller/XxController.ftl");
        // AuthController
        writeFile(dataMap, backendCodePath + "web/controller/AuthController.java", BACKEND_RS_PATH + "web/controller/AuthController.ftl");

        // vo: req
        writeFile(dataMap, backendCodePath + "web/vo/req/" + tablePathName + "InsertReq.java", BACKEND_RS_PATH + "web/vo/req/InsertReq.ftl");
        writeFile(dataMap, backendCodePath + "web/vo/req/" + tablePathName + "QueryReq.java", BACKEND_RS_PATH + "web/vo/req/QueryReq.ftl");
        writeFile(dataMap, backendCodePath + "web/vo/req/" + tablePathName + "UpdateReq.java", BACKEND_RS_PATH + "web/vo/req/UpdateReq.ftl");

        // vo: rsp
        writeFile(dataMap, backendCodePath + "web/vo/rsp/" + tablePathName + "QueryRsp.java", BACKEND_RS_PATH + "web/vo/rsp/QueryRsp.ftl");
        // vo: UserAuthRsp
        writeFile(dataMap, backendCodePath + "web/vo/rsp/UserAuthRsp.java", BACKEND_RS_PATH + "web/vo/rsp/UserAuthRsp.ftl");

        // vo: Pager.java
        writeFile(dataMap, backendCodePath + "web/vo/Pager.java", BACKEND_RS_PATH + "web/vo/Pager.ftl");
        // vo: PagerRsp.java
        writeFile(dataMap, backendCodePath + "web/vo/PagerRsp.java", BACKEND_RS_PATH + "web/vo/PagerRsp.ftl");

        // vo: Response.java
        writeFile(dataMap, backendCodePath + "web/vo/Response.java", BACKEND_RS_PATH + "web/vo/Response.ftl");
    }

    private void generateApplication(NeoMap dataMap) {
        String backendCodePath = dataMap.getString("backendCodePath");
        String appName = dataMap.getString("appName");

        // XxxApplication.java
        writeFile(dataMap, backendCodePath + StringConverter.underLineToBigCamel(appName) + "Application.java", BACKEND_RS_PATH + "Application.ftl");
    }

    private void generateResources(NeoMap dataMap) {
        String backendResourcesPath = dataMap.getString("backendResourcesPath");

        // application.yml
        writeFile(dataMap, backendResourcesPath + "application.yml", BACKEND_RS_PATH + "resources/application.ftl");
        // application-local.yml
        writeFile(dataMap, backendResourcesPath + "application-local.yml", BACKEND_RS_PATH + "resources/application-local.ftl");
        // application-dev.yml
        writeFile(dataMap, backendResourcesPath + "application-dev.yml", BACKEND_RS_PATH + "resources/application-dev.ftl");
        // application-pre.yml
        writeFile(dataMap, backendResourcesPath + "application-pre.yml", BACKEND_RS_PATH + "resources/application-pre.ftl");
        // application-pro.yml
        writeFile(dataMap, backendResourcesPath + "application-pro.yml", BACKEND_RS_PATH + "resources/application-pro.ftl");

        // logback.xml
        writeFile(dataMap, backendResourcesPath + "logback.xml", BACKEND_RS_PATH + "resources/logback.ftl");
    }

    private void generateTest(NeoMap dataMap) {
        String backendTestPath = dataMap.getString("backendTestPath");
        String appName = dataMap.getString("appName");

        // ApplicationTest.java
        writeFile(dataMap, backendTestPath + StringConverter.underLineToBigCamel(appName) + "ApplicationTest.java", BACKEND_RS_PATH + "test/ApplicationTest.ftl");
    }

    private void generateBackend(NeoMap dataMap) {
        String tableNameAfterPre = dataMap.getString("tableNameAfterPre");

        generateAop(dataMap);
        generateConfig(dataMap);
        generateConstant(dataMap);
        generateDao(dataMap, tableNameAfterPre);
        generateException(dataMap);
        generateEntity(dataMap);
        generateService(dataMap, tableNameAfterPre);
        generateTransfer(dataMap, tableNameAfterPre);
        generateWeb(dataMap, tableNameAfterPre);
        generateApplication(dataMap);

        // 生成resource中的文件
        generateResources(dataMap);

        // 生成test文件
        generateTest(dataMap);
    }

    public void generateFront() {
        generateFront(configLoader.getConfigMap());
        System.out.println("front generate finish");
    }

    public void generateBackend() {
        generateBackend(configLoader.getConfigMap());
        System.out.println("backend generate finish");
    }
}
