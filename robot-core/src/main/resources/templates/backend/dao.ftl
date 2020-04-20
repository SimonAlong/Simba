package ${packagePath}.dao;

import com.isyscore.isc.neo.Neo;
import com.isyscore.isc.neo.core.AbstractBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author robot
 */
@Repository
public class ${tablePathName}Dao extends AbstractBizService {

    @Autowired
    private Neo db;

    @Override
    public Neo getDb() {
        return db;
    }

    @Override
    public String getTableName() {
        return "${tableName}";
    }
}
