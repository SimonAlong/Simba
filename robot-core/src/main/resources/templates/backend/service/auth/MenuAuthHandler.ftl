package ${packagePath}.service.auth;

import com.isyscore.os.dev.api.permission.model.domain.MenuAuthorityDomain;

/**
 * @author robot
 */
public interface MenuAuthHandler {

    /**
     * 获取某个菜单的实现
     * @return 菜单以及内部对应的权限配置
     */
    MenuAuthorityDomain getMenuAuth();
}
