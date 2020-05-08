package com.github.simonalong.simba.config;

import com.simonalong.neo.NeoMap;
import lombok.Data;

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
