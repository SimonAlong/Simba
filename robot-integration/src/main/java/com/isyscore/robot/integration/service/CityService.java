package com.isyscore.robot.integration.service;

import com.isyscore.isc.neo.NeoMap;
import com.isyscore.isc.neo.db.NeoPage;
import com.isyscore.robot.integration.dao.CityDao;
import com.isyscore.robot.integration.transfer.CityTransfer;
import com.isyscore.robot.integration.entity.CityDO;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.PagerRsp;
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

    public PagerRsp<List<CityQueryRsp>> pageList(Pager<CityQueryReq> pageReq) {
    PagerRsp<List<CityQueryRsp>> pagerRsp = new PagerRsp<>();

        NeoMap searchMap = NeoMap.from(pageReq.getParam(), NeoMap.NamingChg.UNDERLINE);
        NeoPage page = NeoPage.of(pageReq.getPageNo(), pageReq.getPageSize());

        List<CityQueryRsp> queryRspList = dao.page(CityDO.class, searchMap, page).stream().map(CityTransfer::entityToQueryRsp).collect(Collectors.toList());

        pagerRsp.setDataList(queryRspList);
        pagerRsp.setTotalNum(dao.count(pageReq.getParam()));
        return pagerRsp;
    }
}
