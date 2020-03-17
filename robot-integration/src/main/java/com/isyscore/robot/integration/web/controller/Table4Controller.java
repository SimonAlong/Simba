package com.isyscore.robot.integration.web.controller;

import com.isyscore.robot.integration.web.controller.BaseResponseController;
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
 * @author zhouzhenyong
 * @since 2020/03/17 20:15:13
 */
@Slf4j
@RequestMapping("sequence/table4")
@RestController
public class Table4Controller extends BaseResponseController {

    @Autowired
    private Table4Service table4service;

    @PutMapping("add")
    public Response<Integer> add(@RequestBody Table4InsertReq insertReq){
        try {
            return success(table4service.insert(insertReq));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        try {
            return success(table4service.delete(id));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("update")
    public Response<Integer> update(@RequestBody Table4UpdateReq updateReq) {
        try {
            return success(table4service.update(updateReq));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("pageList")
    public Response<List<Table4QueryRsp>> pageList(@RequestBody Pager<Table4QueryReq> pageReq) {
        try {
            return success(table4service.pageList(pageReq));
        } catch (Exception e) {
            log.error("pageList fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("count")
    public Response<Integer> count(@RequestBody Table4QueryReq countReq) {
        try {
            return success(table4service.count(countReq));
        } catch (Exception e) {
            log.error("count fail", e);
            return fail(e.getMessage());
        }
    }
}
