package com.online4edu.dependencies.mybatis.generator.util;


import com.online4edu.dependencies.mybatis.generator.core.CodeGenerator;
import com.online4edu.dependencies.mybatis.generator.core.ProjectConstant;

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

        ProjectConstant constant = projectPath(projectPackage);
        new CodeGenerator(tableNameArr, author, jdbcUrl, jdbcUser, jdbcPwd, constant, projectPath);
    }


    /**
     * @param projectPackage 项目基础包
     */
    private static ProjectConstant projectPath(String projectPackage) {
        return new ProjectConstant(projectPackage);
    }
}