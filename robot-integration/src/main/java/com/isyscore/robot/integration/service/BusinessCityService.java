package com.isyscore.robot.integration.service;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.db.NeoPage;
import com.isyscore.robot.integration.dao.BusinessCityDao;
import com.isyscore.robot.integration.transfer.BusinessCityTransfer;
import com.isyscore.robot.integration.entity.BusinessCityDO;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.BusinessCityQueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author robot
 */
@Service
public class BusinessCityService {

    @Autowired
    private BusinessCityDao dao;

    public Integer insert(BusinessCityInsertReq insertReq) {
        dao.insert(BusinessCityTransfer.insertReqToEntity(insertReq));
        return 1;
    }

    public Integer delete(Long id) {
        return dao.delete(id);
    }

    public Integer update(BusinessCityUpdateReq updateReq) {
        dao.update(BusinessCityTransfer.updateReqToEntity(updateReq));
        return 1;
    }

    public List<BusinessCityQueryRsp> pageList(Pager<BusinessCityQueryReq> pageReq) {
        return dao.page(NeoMap.from(pageReq.getParam(), NeoMap.NamingChg.UNDERLINE), NeoPage.of(pageReq.getPageIndex(), pageReq.getPageSize()))
            .stream()
            .map(data -> data.as(BusinessCityDO.class))
            .map(BusinessCityTransfer::entityToQueryRsp)
            .collect(Collectors.toList());
    }

    public Integer count(BusinessCityQueryReq pageReq) {
        return dao.count(pageReq);
    }
}
