package com.isyscore.robot.integration.web.controller;

import com.isyscore.robot.integration.aop.AutoCheck;
import com.isyscore.robot.integration.service.CityService;
import com.isyscore.robot.integration.web.vo.Pager;
import com.isyscore.robot.integration.web.vo.Response;
import com.isyscore.robot.integration.web.vo.req.*;
import com.isyscore.robot.integration.web.vo.rsp.CityQueryRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robot
 */
@Slf4j
@RequestMapping("robot/city")
@RestController
public class CityController extends BaseResponseController {

    @Autowired
    private CityService cityservice;

    @AutoCheck
    @PutMapping("add")
    public Response<Integer> add(@RequestBody CityInsertReq insertReq) {
        return success(cityservice.insert(insertReq));
    }

    @AutoCheck
    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        return success(cityservice.delete(id));
    }

    @AutoCheck
    @PostMapping("update")
    public Response<Integer> update(@RequestBody CityUpdateReq updateReq) {
        return success(cityservice.update(updateReq));
    }

    @AutoCheck
    @PostMapping("pageList")
    public Response<List<CityQueryRsp>> pageList(@RequestBody Pager<CityQueryReq> pageReq) {
        return success(cityservice.pageList(pageReq));
    }

    @AutoCheck
    @PostMapping("count")
    public Response<Integer> count(@RequestBody CityQueryReq countReq) {
        return success(cityservice.count(countReq));
    }
}
