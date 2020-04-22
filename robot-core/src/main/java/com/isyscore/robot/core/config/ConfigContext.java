package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.NeoMap;

/**
 * @author shizi
 * @since 2020/4/22 2:57 PM
 */
public interface ConfigContext {

    void visit(NeoMap dataMap);
}
