package ${packagePath}.service;

import com.isyscore.ibo.neo.NeoMap;
import com.isyscore.ibo.neo.db.NeoPage;
import ${packagePath}.dao.${tablePathName}Dao;
import ${packagePath}.transfer.${tablePathName}Transfer;
import ${packagePath}.entity.${tablePathName}DO;
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
    private ${tablePathName}Dao dao;

    public Integer insert(${tablePathName}InsertReq insertReq) {
        dao.insert(${tablePathName}Transfer.insertReqToEntity(insertReq));
        return 1;
    }

    public Integer delete(Long id) {
        return dao.delete(id);
    }

    public Integer update(${tablePathName}UpdateReq updateReq) {
        dao.update(${tablePathName}Transfer.updateReqToEntity(updateReq));
        return 1;
    }

    public List<${tablePathName}QueryRsp> pageList(Pager<${tablePathName}QueryReq> pageReq) {
        return dao.page(NeoMap.from(pageReq.getParam()), NeoPage.of(pageReq.getPageIndex(), pageReq.getPageSize()))
            .stream()
            .map(data -> data.as(${tablePathName}DO.class))
            .map(${tablePathName}Transfer::entityToQueryRsp)
            .collect(Collectors.toList());
    }

    public Integer count(${tablePathName}QueryReq pageReq) {
        return dao.count(pageReq);
    }
}
