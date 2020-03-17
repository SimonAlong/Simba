package ${packagePath}.web.vo.req;

<#if queryReqImport.importDate == 1>
import java.util.Date;
</#if>
<#if queryReqImport.importBigDecimal == 1>
import java.math.BigDecimal;
</#if>
import lombok.Data;

/**
 * @author robot
 */
@Data
public class ${tablePathName}QueryReq {

<#list queryReqFields! as field>
    <#if field.desc?? && field.desc!=''>

    /**
     * ${field.desc}
     */
    </#if>
    private ${field.fieldType} ${field.fieldMeta.codeName};</#list>
}
