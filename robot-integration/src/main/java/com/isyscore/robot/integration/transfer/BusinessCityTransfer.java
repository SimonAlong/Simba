package com.isyscore.robot.integration.transfer;

import com.isyscore.robot.integration.entity.BusinessCityDO;
import com.isyscore.robot.integration.web.vo.req.BusinessCityInsertReq;
import com.isyscore.robot.integration.web.vo.req.BusinessCityUpdateReq;
import com.isyscore.robot.integration.web.vo.rsp.BusinessCityQueryRsp;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

/**
 * @author robot
 */
@UtilityClass
public class BusinessCityTransfer {

    public BusinessCityDO insertReqToEntity(BusinessCityInsertReq req) {
        BusinessCityDO entity = new BusinessCityDO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public BusinessCityDO updateReqToEntity(BusinessCityUpdateReq req) {
        BusinessCityDO entity = new BusinessCityDO();
        BeanUtils.copyProperties(req, entity);
        return entity;
    }

    public BusinessCityQueryRsp entityToQueryRsp(BusinessCityDO entity) {
        BusinessCityQueryRsp rsp = new BusinessCityQueryRsp();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}
