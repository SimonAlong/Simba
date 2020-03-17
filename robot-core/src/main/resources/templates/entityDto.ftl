package ${packagePath}.dto;

import ${packagePath}.entity.${tablePathName}Entity;
import ${packagePath}.vo.req.${tablePathName}InsertReq;
import ${packagePath}.vo.req.${tablePathName}UpdateReq;
import ${packagePath}.vo.rsp.${tablePathName}QueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author ${user}
 * @since ${time}
 */
@UtilityClass
public class ${tablePathName}Dto {

    public ${tablePathName}Entity insertReqToEntity(${tablePathName}InsertReq req) {
        ${tablePathName}Entity entity = new ${tablePathName}Entity();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public ${tablePathName}Entity updateReqToEntity(${tablePathName}UpdateReq req) {
        ${tablePathName}Entity entity = new ${tablePathName}Entity();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public ${tablePathName}QueryRsp entityToQueryRsp(${tablePathName}Entity entity) {
        ${tablePathName}QueryRsp rsp = new ${tablePathName}QueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
