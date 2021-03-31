package com.online4edu.dependencies.mybatis.generator.util;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;

/**
 * 工具类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 21:16
 */
public class AutoUtil {

    private AutoUtil() {
    }

    /**
     * 数据表大写驼峰转换
     */
    public static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    /**
     * 包级转物理地址
     */
    public static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".")
                ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * freemarker 配置
     */
    public static freemarker.template.Configuration ftlConfiguration() {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        cfg.setClassLoaderForTemplateLoading(AutoUtil.class.getClassLoader(), "template/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
}
