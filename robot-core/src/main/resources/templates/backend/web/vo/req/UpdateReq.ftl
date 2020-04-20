package ${packagePath}.web.vo.req;

<#if updateReqImport.importDate == 1>
import java.util.Date;
</#if>
<#if updateReqImport.importBigDecimal == 1>
import java.math.BigDecimal;
</#if>
import lombok.Data;

/**
 * @author robot
 */
@Data
public class ${tablePathName}UpdateReq {

<#list updateReqFields! as field>
    <#if field.desc?? && field.desc!=''>

    /**
     * ${field.desc}
     */
    </#if>
    private ${field.fieldType} ${field.fieldMeta.codeName};</#list>
    /**
     * 更新人名字
     */
    private String updateUserName;
}
