package com.isyscore.robot.integration.dto;

import com.isyscore.robot.integration.entity.Table4DO;
import com.isyscore.robot.integration.web.vo.req.Table4InsertReq;
import com.isyscore.robot.integration.web.vo.req.Table4UpdateReq;
import com.isyscore.robot.integration.web.vo.rsp.Table4QueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author robot
 */
@UtilityClass
public class Table4Dto {

    public Table4DO insertReqToEntity(Table4InsertReq req) {
        Table4DO entity = new Table4DO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public Table4DO updateReqToEntity(Table4UpdateReq req) {
        Table4DO entity = new Table4DO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public Table4QueryRsp entityToQueryRsp(Table4DO entity) {
        Table4QueryRsp rsp = new Table4QueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
