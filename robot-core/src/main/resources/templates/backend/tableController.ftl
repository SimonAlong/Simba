package ${packagePath}.controller;

import ${packagePath}.web.controller.BaseResponseController;
import ${packagePath}.constants.AdminConstant;
import ${packagePath}.service.${tablePathName}Service;
import ${packagePath}.vo.Pager;
import ${packagePath}.vo.Response;
import ${packagePath}.vo.req.*;
import ${packagePath}.vo.rsp.${tablePathName}QueryRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ${user}
 * @since ${time}
 */
@Slf4j
<#if appName!='portal'>@RequestMapping("${appName}/${tableUrlName}")
<#else>@RequestMapping("platform/${appName}/${tableUrlName}")</#if>
@RestController
public class ${tablePathName}Controller extends BaseResponseController {

    @Autowired
    private ${tablePathName}Service ${tablePathNameLower}service;

    @PutMapping("add")
    public Response<Integer> add(@RequestBody ${tablePathName}InsertReq insertReq){
        try {
            return success(${tablePathNameLower}service.insert(insertReq));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public Response<Integer> delete(@PathVariable Long id) {
        try {
            return success(${tablePathNameLower}service.delete(id));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("update")
    public Response<Integer> update(@RequestBody ${tablePathName}UpdateReq updateReq) {
        try {
            return success(${tablePathNameLower}service.update(updateReq));
        } catch (Exception e) {
            log.error("add fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("pageList")
    public Response<List<${tablePathName}QueryRsp>> pageList(@RequestBody Pager<${tablePathName}QueryReq> pageReq) {
        try {
            return success(${tablePathNameLower}service.pageList(pageReq));
        } catch (Exception e) {
            log.error("pageList fail", e);
            return fail(e.getMessage());
        }
    }

    @PostMapping("count")
    public Response<Integer> count(@RequestBody ${tablePathName}QueryReq countReq) {
        try {
            return success(${tablePathNameLower}service.count(countReq));
        } catch (Exception e) {
            log.error("count fail", e);
            return fail(e.getMessage());
        }
    }
}
