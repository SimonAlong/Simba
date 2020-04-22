package com.isyscore.robot.core.config;

import com.isyscore.isc.neo.NeoMap;
import com.isyscore.robot.core.entity.FieldInfo;
import com.isyscore.robot.core.entity.FieldMeta;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author shizi
 * @since 2020/4/22 2:35 PM
 */
@Data
public class FrontConfig implements ConfigContext {

    /**
     * 前端代码生成路径
     */
    private String frontCodePath;


    @Override
    public void visit(NeoMap dataMap) {
        if (null == dataMap) {
            return;
        }
        dataMap.put("expandExist", 0);
        dataMap.put("frontCodePath", frontCodePath);
    }
}
