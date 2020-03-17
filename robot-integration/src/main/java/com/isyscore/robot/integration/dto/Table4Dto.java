package com.isyscore.robot.integration.dto;

import com.isyscore.robot.integration.entity.Table4Entity;
import com.isyscore.robot.integration.vo.req.Table4InsertReq;
import com.isyscore.robot.integration.vo.req.Table4UpdateReq;
import com.isyscore.robot.integration.vo.rsp.Table4QueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 19:39:28
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
