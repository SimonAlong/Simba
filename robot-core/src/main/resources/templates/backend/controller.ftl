package ${packagePath}.web.controller;

import ${packagePath}.aop.AutoCheck;
import ${packagePath}.service.${tablePathName}Service;
import ${packagePath}.web.vo.Pager;
import ${packagePath}.web.vo.Response;
import ${packagePath}.web.vo.req.*;
import ${packagePath}.web.vo.rsp.${tablePathName}QueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robot
 */
@RequestMapping("${appName}/${tableUrlName}")
@RestController
public class ${tablePathName}Controller extends BaseResponseController {

    @Autowired
    private ${tablePathName}Service ${tablePathNameLower}service;

    @AutoCheck
    @PutMapping("add")
    public Response<Integer> add(@RequestBody ${tablePathName}InsertReq insertReq) {
        return success(${tablePathNameLower}service.insert(insertReq));
    }

    @AutoCheck
    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        return success(${tablePathNameLower}service.delete(id));
    }

    @AutoCheck
    @PostMapping("update")
    public Response<Integer> update(@RequestBody ${tablePathName}UpdateReq updateReq) {
        return success(${tablePathNameLower}service.update(updateReq));
    }

    @AutoCheck
    @PostMapping("pageList")
    public Response<List<${tablePathName}QueryRsp>> pageList(@RequestBody Pager<${tablePathName}QueryReq> pageReq) {
        return success(${tablePathNameLower}service.pageList(pageReq));
    }

    @AutoCheck
    @PostMapping("count")
    public Response<Integer> count(@RequestBody ${tablePathName}QueryReq countReq) {
        return success(${tablePathNameLower}service.count(countReq));
    }
}
