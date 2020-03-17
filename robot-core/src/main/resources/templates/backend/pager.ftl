package ${packagePath}.web.vo;

import lombok.Data;

/**
 * @author ${user}
 * @since ${time}
 */
@Data
public class Pager<T> {

    private Integer pageNo;
    private Integer pageSize;
    private T param;

    public Integer getPageIndex() {
        return pageNo > 1 ? (pageNo - 1) * pageSize : 0;
    }
}
