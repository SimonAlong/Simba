package ${packagePath}.web.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author robot
 */
@Data
@Accessors(chain = true)
public class AccountInfo {

    /**
     * 邮箱
     */
    private String mail;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 展示的中文花名（中文唯一）
     */
    private String displayName;
    /**
     * 姓
     */
    private String sn;
    /**
     * 花名字母（用户名不唯一）
     */
    private String cn;
}
