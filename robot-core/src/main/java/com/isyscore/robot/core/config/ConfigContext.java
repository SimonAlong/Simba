package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.NeoMap;

/**
 * @author shizi
 * @since 2020/4/22 2:57 PM
 */
public interface ConfigContext {

    /**
     * 参数作为访客，读取子类的数据
     *
     * @param dataMap 访客
     */
    void visit(NeoMap dataMap);
}
