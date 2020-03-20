package com.isyscore.robot.integration.web.controller;

import com.isyscore.robot.integration.aop.AutoCheck;
import com.isyscore.robot.integration.service.BusinessCityService;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.Response;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.BusinessCityQueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robot
 */
@RequestMapping("robot/business/city")
@RestController
public class BusinessCityController extends BaseResponseController {

    @Autowired
    private BusinessCityService businessCityservice;

    @AutoCheck
    @PutMapping("add")
    public Response<Integer> add(@RequestBody BusinessCityInsertReq insertReq) {
        return success(businessCityservice.insert(insertReq));
    }

    @AutoCheck
    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        return success(businessCityservice.delete(id));
    }

    @AutoCheck
    @PostMapping("update")
    public Response<Integer> update(@RequestBody BusinessCityUpdateReq updateReq) {
        return success(businessCityservice.update(updateReq));
    }

    @AutoCheck
    @PostMapping("pageList")
    public Response<List<BusinessCityQueryRsp>> pageList(@RequestBody Pager<BusinessCityQueryReq> pageReq) {
        return success(businessCityservice.pageList(pageReq));
    }

    @AutoCheck
    @PostMapping("count")
    public Response<Integer> count(@RequestBody BusinessCityQueryReq countReq) {
        return success(businessCityservice.count(countReq));
    }
}
