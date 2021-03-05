package com.online4edu.dependencies.mybatis;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

/**
 * mybatis 工具类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:02
 */
public final class MyBatisUtils {

    public static Log getLog(Object object) {
        return LogFactory.getLog(object.getClass());
    }
}
