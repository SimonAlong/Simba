package com.isyscore.robot.integration.service;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.db.NeoPage;
import com.isyscore.robot.integration.dao.Table4Dao;
import com.isyscore.robot.integration.transfer.Table4Transfer;
import com.isyscore.robot.integration.entity.Table4DO;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.Table4QueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author robot
 */
@Service
public class Table4Service {

    @Autowired
    private Table4Dao dao;

    public Integer insert(Table4InsertReq insertReq) {
        dao.insert(Table4Transfer.insertReqToEntity(insertReq));
        return 1;
    }

    public Integer delete(Long id) {
        return dao.delete(id);
    }

    public Integer update(Table4UpdateReq updateReq) {
        dao.update(Table4Transfer.updateReqToEntity(updateReq));
        return 1;
    }

    public List<Table4QueryRsp> pageList(Pager<Table4QueryReq> pageReq) {
        return dao.page(NeoMap.from(pageReq.getPageIndex()), NeoPage.of(pageReq.getPageIndex(), pageReq.getPageSize()))
            .stream()
            .map(data -> data.as(Table4DO.class))
            .map(Table4Transfer::entityToQueryRsp)
            .collect(Collectors.toList());
    }

    public Integer count(Table4QueryReq pageReq) {
        return dao.count(pageReq);
    }
}
