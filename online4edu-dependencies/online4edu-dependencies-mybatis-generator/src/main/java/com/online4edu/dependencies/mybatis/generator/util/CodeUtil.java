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
        System.out.println("======================= 开始初始化项目基本信息 =======================");
        ProjectProperties projectProperties = ProjectProperties.getInstance();
        projectProperties.init(projectPackage);

        System.out.println("init project configuration...");
        System.out.println("======================= 开始初始化JDBC连接信息 =======================");
        JdbcProperties jdbcProperties = JdbcProperties.getInstance();
        jdbcProperties.init(author, jdbcUrl, jdbcUser, jdbcPwd);

        System.out.println("init jdbc configuration...");
        System.out.println("=========================== 完成初始化 ============================");
        System.out.println("=========================== 开始生成文件 ===========================");
        System.out.println("");

        new CodeGenerator(tableNameArr, projectPath);

        System.out.println("");
        System.out.println("========================== 自动化生成完成 ==========================");
        System.out.println("============= 在使用时需要在所属项目中增加如下 maven 依赖 =============");
        System.out.println("");
        System.out.println("<dependency>");
        System.out.println("    <groupId>com.online4edu</groupId>");
        System.out.println("    <artifactId>online4edu-dependencies</artifactId>");
        System.out.println("</dependency>");
    }
}