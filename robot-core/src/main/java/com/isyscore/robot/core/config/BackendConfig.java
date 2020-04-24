package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.NeoMap;
import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shizi
 * @since 2020/4/22 2:54 PM
 */
@Data
public class BackendConfig implements ConfigContext {

    private String group;
    private String artifact;
    private String name;
    private String description;
    /**
     * Package name
     */
    private String packagePath;
    /**
     * 后端代码的项目模块路径
     */
    private String projectPath;
    /**
     * 后端代码生成路径
     */
    private String backendCodePath;
    /**
     * 后端代码的测试路径
     */
    private String backendTestPath;
    /**
     * 后端资源文件生成路径
     */
    private String backendResourcesPath;

    public void setBackendProjectPath(String projectPath) {
        this.projectPath = projectPath;
        if (projectPath.endsWith("/")) {
            this.backendCodePath = projectPath + "src/main/java/";
            this.backendTestPath = projectPath + "src/test/java/";
            this.backendResourcesPath = projectPath + "src/main/resources/";
        } else {
            this.backendCodePath = projectPath + "/src/main/java/";
            this.backendTestPath = projectPath + "/src/test/java/";
            this.backendResourcesPath = projectPath + "/src/main/resources/";
            this.projectPath = projectPath + "/";
        }
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        this.backendCodePath += packagePath.replace(".", "/") + "/";
        this.backendTestPath += packagePath.replace(".", "/") + "/";
    }

    @Override
    public void visit(NeoMap dataMap) {
        if (null == dataMap) {
            return;
        }
        dataMap.put("group", group);
        dataMap.put("artifact", artifact);
        dataMap.put("name", name);
        dataMap.put("description", description);
        dataMap.put("packagePath", packagePath);

        dataMap.put("user", System.getProperty("user.name"));
        dataMap.put("time", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

        dataMap.put("projectPath", projectPath);
        dataMap.put("backendCodePath", backendCodePath);
        dataMap.put("backendTestPath", backendTestPath);
        dataMap.put("backendResourcesPath", backendResourcesPath);
    }
}
