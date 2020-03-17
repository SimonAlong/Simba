package ${packagePath}.service;

import ${packagePath}.dto.${tablePathName}Dto;
import ${packagePath}.mapper.ext.${tablePathName}ExtMapper;
import ${packagePath}.web.vo.Pager;
import ${packagePath}.web.vo.req.*;
import ${packagePath}.web.vo.rsp.${tablePathName}QueryRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author robot
 */
@Service
public class ${tablePathName}Service {

    @Autowired
    private ${tablePathName}ExtMapper mapper;

    public Integer insert(${tablePathName}InsertReq insertReq) {
        return mapper.insert(${tablePathName}Dto.insertReqToEntity(insertReq));
    }

    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    public Integer update(${tablePathName}UpdateReq updateReq) {
        return mapper.updateById(${tablePathName}Dto.updateReqToEntity(updateReq));
    }

    public List<${tablePathName}QueryRsp> pageList(Pager<${tablePathName}QueryReq> pageReq) {
        // todo 自己在mapper中实现
        return mapper.pageList(pageReq.getPageIndex(), pageReq.getPageSize(), pageReq.getParam())
            .stream()
            .map(${tablePathName}Dto::entityToQueryRsp).collect(Collectors.toList());
    }

    public Integer count(${tablePathName}QueryReq pageReq) {
        // todo 自己在mapper中实现
         return mapper.count(pageReq);
    }
}
