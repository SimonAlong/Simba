package com.isyscore.robot.core.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author shizi
 * @since 2019/12/3 11:48 上午
 */
public class FreeMarkerTemplateUtil {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static{
        //这里比较重要，用来指定加载模板所在的路径
        CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtil.class, "/templates"));
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static Template getTemplate(String templateName) {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
