package com.isyscore.robot.integration.dto;

import com.isyscore.robot.integration.entity.Table4DO;
import com.isyscore.robot.integration.web.vo.req.Table4InsertReq;
import com.isyscore.robot.integration.web.vo.req.Table4UpdateReq;
import com.isyscore.robot.integration.web.vo.rsp.Table4QueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 20:15:13
 */
@UtilityClass
public class Table4Dto {

    public Table4Entity insertReqToEntity(Table4InsertReq req) {
        Table4Entity entity = new Table4Entity();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public Table4Entity updateReqToEntity(Table4UpdateReq req) {
        Table4Entity entity = new Table4Entity();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public Table4QueryRsp entityToQueryRsp(Table4Entity entity) {
        Table4QueryRsp rsp = new Table4QueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
