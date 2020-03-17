package com.isyscore.robot.integration.dao;

import com.isyscore.ibo.neo.Neo;
import com.isyscore.ibo.neo.core.AbstractBizService;
import com.isyscore.ibo.neo.core.DbSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author shizi
 * @since 2020/3/17 下午8:35
 */
@Repository
public class Table4Dao extends AbstractBizService {

    @Autowired
    private Neo db;

    @Override
    public DbSync getDb() {
        return db;
    }

    @Override
    public String getTableName() {
        return "neo_table4";
    }
}
