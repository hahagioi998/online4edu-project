package com.online4edu.dependencies.mybatis.generator.util;


import com.online4edu.dependencies.mybatis.generator.core.CodeGenerator;
import com.online4edu.dependencies.mybatis.generator.core.JdbcProperties;
import com.online4edu.dependencies.mybatis.generator.core.ProjectProperties;

import java.io.File;

/**
 * 数据表 Mapper、Model、VO、Mapper.xml 文件生成工具类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 18/10/2018 13:05
 */
public class CodeUtil {

    public CodeUtil() {
    }

    /**
     * @param tableNameArr   数据表二维数组
     *                       e.g:{{"tableName", "domainName", "description", "pk","pkDataType"}}
     * @param author         作者
     * @param jdbcUrl        数据库地址
     * @param jdbcUser       数据库账号
     * @param jdbcPwd        数据库密码
     * @param projectPath    项目输出路径
     * @param projectPackage 包
     *                       e.g:com.mingrn
     */
    public static void create(String[][] tableNameArr, String author, String jdbcUrl,
                              String jdbcUser, String jdbcPwd, String projectPath, String projectPackage) {

        File file = new File(projectPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 初始化基本信息
        ProjectProperties projectProperties = ProjectProperties.getInstance();
        projectProperties.init(projectPackage);

        JdbcProperties jdbcProperties = JdbcProperties.getInstance();
        jdbcProperties.init(author, jdbcUrl, jdbcUser, jdbcPwd);

        new CodeGenerator(tableNameArr, projectPath);
    }
}