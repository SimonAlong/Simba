package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.NeoMap;
import lombok.Getter;

import java.util.List;

/**
 * @author shizi
 * @since 2020/4/22 3:02 PM
 */
public class ConfigLoader {

    @Getter
    private NeoMap configMap = NeoMap.of();

    public void load(List<ConfigContext> configContextList) {
        if (null == configContextList || configContextList.isEmpty()) {
            return;
        }

        configContextList.forEach(e -> e.visit(configMap));
    }
}
