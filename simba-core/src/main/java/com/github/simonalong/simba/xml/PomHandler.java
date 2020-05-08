package com.github.simonalong.simba.xml;

import com.github.simonalong.simba.util.FileUtil;
import com.github.simonalong.simba.xml.element.DependencyElement;
import com.github.simonalong.simba.xml.element.ParentElement;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/4/22 5:37 PM
 */
@Data
public class PomHandler {

    /**
     * 后端代码的项目模块路径
     */
    private String projectPath;
    private ParentElement parentElement;
    private String groupId;
    private String artifactId;
    private String version = "1.0.0-SNAPSHOT";
    private String name;
    private String description;
    private Set<DependencyElement> dependencies = new LinkedHashSet<>();

    public PomHandler() {
        initDefaultDependency();
    }

    public void addDependency(DependencyElement dependencyElement) {
        dependencies.add(dependencyElement);
    }

    public void setProjectPath(String projectPath) {
        if (projectPath.endsWith("/")) {
            this.projectPath = projectPath;
        } else {
            this.projectPath = projectPath + "/";
        }
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        this.name = artifactId;
    }

    public void generate() {
        try {
            SAXReader reader = new SAXReader();
            String pomFile = projectPath + "pom.xml";
            if (!FileUtil.exist(pomFile)) {
                throw new RuntimeException("文件：" + pomFile + " 不存在");
            }
            Document document = reader.read(new File(pomFile));
            Element root = document.getRootElement();

            addParent(root);
            addBaseInfo(root);
            addDependencies(root);

            write(document);
        } catch (Throwable e) {
            throw new RuntimeException(e.getCause());
        }
    }

    /**
     * 添加parent依赖
     */
    private void addParent(Element root) {
        Element parent = root.element("parent");
        if (null != parentElement && null != parent) {
            if (StringUtils.isEmpty(parent.getText())) {
                String groupId = parentElement.getGroupId();
                if (!StringUtils.isEmpty(groupId)) {
                    parent.addElement("groupId").setText(groupId);
                }

                String artifactId = parentElement.getArtifactId();
                if (!StringUtils.isEmpty(artifactId)) {
                    parent.addElement("artifactId").setText(artifactId);
                }

                String version = parentElement.getVersion();
                if (!StringUtils.isEmpty(version)) {
                    parent.addElement("version").setText(version);
                }
            }
        }
    }

    /**
     * 添加 groupId, artifactId，version，name，description，对应的信息
     */
    private void addBaseInfo(Element root) {
        root.element("groupId").setText(groupId);
        root.element("artifactId").setText(artifactId);
        root.element("version").setText(version);
        root.element("name").setText(name);
        root.element("description").setText(description);
    }

    private void addDependencies(Element root) {
        Element dependenciesElement = root.element("dependencies");
        dependencies.forEach(d -> {
            Element dependencyElement = dependenciesElement.addElement("dependency");
            if (!StringUtils.isEmpty(d.getGroupId())) {
                dependencyElement.addElement("groupId").setText(d.getGroupId());
            }
            if (!StringUtils.isEmpty(d.getArtifactId())) {
                dependencyElement.addElement("artifactId").setText(d.getArtifactId());
            }
            if (!StringUtils.isEmpty(d.getVersion())) {
                dependencyElement.addElement("version").setText(d.getVersion());
            }
            if (!StringUtils.isEmpty(d.getClassifier())) {
                dependencyElement.addElement("classifier").setText(d.getClassifier());
            }
            if (!StringUtils.isEmpty(d.getScope())) {
                dependencyElement.addElement("scope").setText(d.getScope());
            }
            if (!StringUtils.isEmpty(d.getSystemPath())) {
                dependencyElement.addElement("systemPath").setText(d.getSystemPath());
            }
            if (!StringUtils.isEmpty(d.getOptional())) {
                dependencyElement.addElement("optional").setText(d.getOptional());
            }
        });
    }

    private void write(Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        // 缩进设置为代码的缩进
        format.setIndent("    ");
        // 第二行空行
        format.setNewLineAfterDeclaration(false);
        XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(projectPath + "/pom.xml"))), format);
        xmlWriter.write(document);
        xmlWriter.close();
    }

    private void initDefaultDependency() {
        // 基本依赖
        dependencies.add(DependencyElement.builder().groupId("org.projectlombok").artifactId("lombok").build());

        // 内部依赖项
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build());
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build());
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-aop").build());
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-test").build());
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-jdbc").build());
        dependencies.add(DependencyElement.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-logging").build());

        dependencies.add(DependencyElement.builder().groupId("com.simonalong.mikilin").artifactId("mikilin").version("1.5.0").build());
        dependencies.add(DependencyElement.builder().groupId("com.simonalong.neo").artifactId("neo").version("0.5.0").build());
    }

}
