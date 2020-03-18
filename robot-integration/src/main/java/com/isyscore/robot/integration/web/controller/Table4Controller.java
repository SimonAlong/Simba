package com.isyscore.robot.integration.web.controller;

import com.isyscore.robot.integration.aop.AutoCheck;
import com.isyscore.robot.integration.service.Table4Service;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.Response;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.Table4QueryRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robot
 */
@Slf4j
@RequestMapping("sequence/table4")
@RestController
public class Table4Controller extends BaseResponseController {

    @Autowired
    private Table4Service table4service;

    @AutoCheck
    @PutMapping("add")
    public Response<Integer> add(@RequestBody Table4InsertReq insertReq) {
        return success(table4service.insert(insertReq));
    }

    @AutoCheck
    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        return success(table4service.delete(id));
    }

    @AutoCheck
    @PostMapping("update")
    public Response<Integer> update(@RequestBody Table4UpdateReq updateReq) {
        return success(table4service.update(updateReq));
    }

    @AutoCheck
    @PostMapping("pageList")
    public Response<List<Table4QueryRsp>> pageList(@RequestBody Pager<Table4QueryReq> pageReq) {
        return success(table4service.pageList(pageReq));
    }

    @AutoCheck
    @PostMapping("count")
    public Response<Integer> count(@RequestBody Table4QueryReq countReq) {
        return success(table4service.count(countReq));
    }
}
