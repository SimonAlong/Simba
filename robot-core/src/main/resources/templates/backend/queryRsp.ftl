package ${packagePath}.web.vo.rsp;

import lombok.Data;
<#if queryRspImport.importDate == 1>
import java.util.Date;
</#if>
<#if queryRspImport.importBigDecimal == 1>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
 * @author ${user}
 * @since ${time}
 */
@Data
public class ${tablePathName}QueryRsp implements Serializable {

<#list queryRspFields! as field>
    <#if field.desc?? && field.desc!=''>

    /**
     * ${field.desc}
     */
    </#if>
    private ${field.fieldType} ${field.fieldMeta.codeName};</#list>
}
