package com.isyscore.robot.integration.service;

import com.isyscore.robot.integration.dto.Table4Dto;
import com.isyscore.robot.integration.mapper.ext.Table4ExtMapper;
import com.isyscore.robot.integration.vo.Pager;
import com.isyscore.robot.integration.vo.req.*;
import com.isyscore.robot.integration.vo.rsp.Table4QueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouzhenyong
 * @since 2020/03/17 19:39:28
 */
@Service
public class Table4Service {

    @Autowired
    private Table4ExtMapper mapper;

    public Integer insert(Table4InsertReq insertReq) {
        return mapper.insert(Table4Dto.insertReqToEntity(insertReq));
    }

    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    public Integer update(Table4UpdateReq updateReq) {
        return mapper.updateById(Table4Dto.updateReqToEntity(updateReq));
    }

    public List<Table4QueryRsp> pageList(Pager<Table4QueryReq> pageReq) {
        // todo 自己在mapper中实现
        return mapper.pageList(pageReq.getPageIndex(), pageReq.getPageSize(), pageReq.getParam())
            .stream()
            .map(Table4Dto::entityToQueryRsp).collect(Collectors.toList());
    }

    public Integer count(Table4QueryReq pageReq) {
        // todo 自己在mapper中实现
         return mapper.count(pageReq);
    }
}
