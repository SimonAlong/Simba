package com.isyscore.robot.integration.transfer;

import com.isyscore.robot.integration.entity.CityDO;
import com.isyscore.robot.integration.web.vo.req.CityInsertReq;
import com.isyscore.robot.integration.web.vo.req.CityUpdateReq;
import com.isyscore.robot.integration.web.vo.rsp.CityQueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author robot
 */
@UtilityClass
public class CityTransfer {

    public CityDO insertReqToEntity(CityInsertReq req) {
        CityDO entity = new CityDO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public CityDO updateReqToEntity(CityUpdateReq req) {
        CityDO entity = new CityDO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public CityQueryRsp entityToQueryRsp(CityDO entity) {
        CityQueryRsp rsp = new CityQueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
