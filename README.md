# isc-robot介绍
该组件可以一键式生成整个前后端项目。其中前端为采用React的Ant Design Pro框架，后端为基本的springBoot项目。前端需要以ibo-front-base项目为模板进行创建新的业务前端项目。后端可以采用空项目即可。

## 快速入门
### 引入
```xml
<dependency>
  <groupId>com.isyscore.robot</groupId>
  <artifactId>robot-core</artifactId>
  <!--使用时候请替换为具体的版本-->
  <version>${latest.release.version}</version>
</dependency>
```

### 生成
这里前后端你都可以生成，可以根据自己自行选择生成前端还是生成后端，还是两者都生成。<br/>
注意：<br/>
1.该工具每次生成是一个库中的一个表，生成前端的一个页面，以及生成后端的：Controller、Service、Dao和Entity等等数据
2.该工具生成的后端涉及到的框架：
- web框架：spring-boot
- Orm框架（自研）：ibo-neo
- 核查框架（自研）：ibo-mikilin<br/>

3.该工具生成的前端<br/>
- 前端语言：React<br/>
- 路由框架：dva

<br/>
<br/>
如果第一次使用，则先基于"ibo-front-base"为模板，创建自己的前端项目（具体方式可以将当前项目天下远程然后上传，或者新建项目然后拷贝）。如果是已经开发过不少的前端，也可以使用该框架，因为这个仅仅是一个工具

<br/>

```java
@Test
@SuppressWarnings("all")
public void genFront2() {
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
    codeGen.setFrontCodePath("/Users/zhouzhenyong/project/isyscore/ibo-front-base");

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
    codeGen.generateFront();

    // 生成后端
    codeGen.generateBackend();
}
```

### 启动
#### 前端启动：
> npm run start

#### 后端启动：
1.本机启动直接启动即可
2.外部启动，直接启动即可
> java -jar xxx xxxx.jar
