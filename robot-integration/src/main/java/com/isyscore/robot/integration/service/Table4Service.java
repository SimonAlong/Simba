package com.isyscore.robot.integration.service;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.db.NeoPage;
import com.isyscore.robot.integration.dao.Table4Dao;
import com.isyscore.robot.integration.dto.Table4Dto;
import com.isyscore.robot.integration.entity.Table4DO;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.Table4QueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 20:33:25
 */
@Service
public class Table4Service {

    @Autowired
    private Table4Dao dao;

    public Integer insert(Table4InsertReq insertReq) {
        dao.insert(Table4Dto.insertReqToEntity(insertReq));
        return 1;
    }

    public Integer delete(Long id) {
        return dao.delete(id);
    }

    public Integer update(Table4UpdateReq updateReq) {
        dao.update(Table4Dto.updateReqToEntity(updateReq));
        return 1;
    }

    public List<Table4QueryRsp> pageList(Pager<Table4QueryReq> pageReq) {
        return dao.page(NeoMap.from(pageReq.getParam()), NeoPage.of(pageReq.getPageIndex(), pageReq.getPageSize()))
            .stream()
            .map(dataMap -> dataMap.as(Table4DO.class))
            .map(Table4Dto::entityToQueryRsp)
            .collect(Collectors.toList());
    }

    public Integer count(Table4QueryReq pageReq) {
        return dao.count(pageReq);
    }
}
