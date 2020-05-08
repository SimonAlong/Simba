package ${packagePath}.service;

import com.simonalong.neo.NeoMap;
import com.simonalong.neo.db.NeoPage;
import ${packagePath}.dao.${tablePathName}Dao;
import ${packagePath}.transfer.${tablePathName}Transfer;
import ${packagePath}.entity.${tablePathName}DO;
import ${packagePath}.web.vo.Pager;
import ${packagePath}.web.vo.PagerRsp;
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

    public PagerRsp<List<${tablePathName}QueryRsp>> pageList(Pager<${tablePathName}QueryReq> pageReq) {
        PagerRsp<List<${tablePathName}QueryRsp>> pagerRsp = new PagerRsp<>();

        NeoMap searchMap = NeoMap.from(pageReq.getParam(), NeoMap.NamingChg.UNDERLINE);
        NeoPage page = NeoPage.of(pageReq.getPageNo(), pageReq.getPageSize());

        List<${tablePathName}QueryRsp> queryRspList = dao.page(${tablePathName}DO.class, searchMap, page).stream().map(${tablePathName}Transfer::entityToQueryRsp).collect(Collectors.toList());

        pagerRsp.setDataList(queryRspList);
        pagerRsp.setTotalNum(dao.count(pageReq.getParam()));
        return pagerRsp;
    }
}
