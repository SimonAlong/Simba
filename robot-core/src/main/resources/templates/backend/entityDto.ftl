package ${packagePath}.dto;

import ${packagePath}.entity.${tablePathName}DO;
import ${packagePath}.web.vo.req.${tablePathName}InsertReq;
import ${packagePath}.web.vo.req.${tablePathName}UpdateReq;
import ${packagePath}.web.vo.rsp.${tablePathName}QueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author ${user}
 * @since ${time}
 */
@UtilityClass
public class ${tablePathName}Dto {

    public ${tablePathName}DO insertReqToEntity(${tablePathName}InsertReq req) {
        ${tablePathName}DO entity = new ${tablePathName}DO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public ${tablePathName}DO updateReqToEntity(${tablePathName}UpdateReq req) {
        ${tablePathName}DO entity = new ${tablePathName}DO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public ${tablePathName}QueryRsp entityToQueryRsp(${tablePathName}DO entity) {
        ${tablePathName}QueryRsp rsp = new ${tablePathName}QueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
