package ${packagePath}.web.vo;

import com.simonalong.mikilin.annotation.Check;
import com.simonalong.mikilin.annotation.Matcher;
import lombok.Data;

/**
 * @author robot
 */
@Data
public class Pager<T> {

    @Matcher(range = "[0, )", errMsg = "分页数据不满足")
    private Integer pageNo;
    @Matcher(range = "[0, )", errMsg = "pageSize数据不满足")
    private Integer pageSize;
    @Check
    private T param;

    public Integer getPageIndex() {
        return pageNo > 1 ? (pageNo - 1) * pageSize : 0;
    }
}
