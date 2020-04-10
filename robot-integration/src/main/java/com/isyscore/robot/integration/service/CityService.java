package com.isyscore.robot.integration.service;

import com.isyscore.isc.neo.NeoMap;
import com.isyscore.isc.neo.db.NeoPage;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.robot.integration.dao.CityDao;
import com.isyscore.robot.integration.transfer.CityTransfer;
import com.isyscore.robot.integration.entity.CityDO;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.CityQueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author robot
 */
@Service
public class CityService {

    @Autowired
    private CityDao dao;
    @Autowired
    private PermissionService permissionService;

    public Integer insert(CityInsertReq insertReq) {
        dao.insert(CityTransfer.insertReqToEntity(insertReq));
        return 1;
    }

    public Integer delete(Long id) {
        return dao.delete(id);
    }

    public Integer update(CityUpdateReq updateReq) {
        dao.update(CityTransfer.updateReqToEntity(updateReq));
        return 1;
    }

    public List<CityQueryRsp> pageList(Pager<CityQueryReq> pageReq) {
        return dao.page(NeoMap.from(pageReq.getParam(), NeoMap.NamingChg.UNDERLINE), NeoPage.of(pageReq.getPageIndex(), pageReq.getPageSize()))
            .stream()
            .map(data -> data.as(CityDO.class))
            .map(CityTransfer::entityToQueryRsp)
            .collect(Collectors.toList());
    }

    public Integer count(CityQueryReq pageReq) {
        return dao.count(pageReq);
    }
}
