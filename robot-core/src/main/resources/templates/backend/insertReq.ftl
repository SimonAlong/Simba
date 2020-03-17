package ${packagePath}.vo.req;

<#if insertReqImport.importDate == 1>
import java.util.Date;
</#if>
<#if insertReqImport.importBigDecimal == 1>
import java.math.BigDecimal;
</#if>
import lombok.Data;

/**
 * @author ${user}
 * @since ${time}
 */
@Data
public class ${tablePathName}InsertReq {

<#list insertReqFields! as field>
    <#if field.desc?? && field.desc!=''>

    /**
     * ${field.desc}
     */
    </#if>
    private ${field.fieldType} ${field.fieldMeta.codeName};</#list>
    /**
     * 创建人名字
     */
    private String createUserName;
}
