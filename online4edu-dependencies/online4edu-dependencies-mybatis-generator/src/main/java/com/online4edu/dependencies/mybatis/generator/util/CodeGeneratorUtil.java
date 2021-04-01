package com.online4edu.dependencies.mybatis.generator.util;


import com.online4edu.dependencies.mybatis.generator.core.Generator;
import com.online4edu.dependencies.mybatis.generator.domain.JdbcProperties;
import com.online4edu.dependencies.mybatis.generator.domain.ProjectProperties;
import com.online4edu.dependencies.mybatis.generator.domain.TableSign;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 数据表 Mapper、Model、VO、Mapper.xml 文件生成工具类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 18/10/2018 13:05
 */
public class CodeGeneratorUtil {

    public CodeGeneratorUtil() {
    }

    public static void create(String[][] tableNameArr, String author, String jdbcUrl, String projectPath, String projectPackage) {
        create(tableNameArr, author, jdbcUrl, "root", "root", projectPath, projectPackage);
    }

    public static void create(String[][] tableNameArr, String author, String jdbcUrl,
                              String jdbcUser, String jdbcPwd, String projectPath, String projectPackage) {
        create(tableNameArr, author, jdbcUrl, jdbcUser, jdbcPwd, projectPath, projectPackage, null);
    }

    /**
     * @param tableNameArr   数据表二维数组
     *                       e.g:{{"tableName", "domainName", "description", "pk","pkDataType"}}
     * @param author         作者
     * @param jdbcUrl        数据库地址
     * @param jdbcUser       数据库账号
     * @param jdbcPwd        数据库密码
     * @param projectPath    项目输出路径
     * @param projectPackage 包, 如: com.mingrn
     * @param stripPrefix    剥离数据库表前缀
     */
    public static void create(String[][] tableNameArr, String author, String jdbcUrl,
                              String jdbcUser, String jdbcPwd, String projectPath,
                              String projectPackage, String stripPrefix) {

        File file = new File(projectPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 初始化基本信息
        System.out.println("======================= 开始初始化项目基本信息 =======================");
        ProjectProperties projectProperties = ProjectProperties.getInstance();
        projectProperties.init(projectPath, projectPackage, stripPrefix);

        System.out.println("init project configuration...");
        System.out.println("======================= 开始初始化JDBC连接信息 =======================");
        JdbcProperties jdbcProperties = JdbcProperties.getInstance();
        jdbcProperties.init(author, jdbcUrl, jdbcUser, jdbcPwd);

        System.out.println("init jdbc configuration...");
        System.out.println("=========================== 完成初始化 ============================");
        System.out.println("=========================== 开始生成文件 ===========================");
        System.out.println();

        generator(tableNameArr);

        System.out.println();
        System.out.println("========================== 自动化生成完成 ==========================");
        System.out.println("============= 在使用时需要在所属项目中增加如下 maven 依赖 =============");
        System.out.println();
        System.out.println("<dependency>");
        System.out.println("    <groupId>com.online4edu</groupId>");
        System.out.println("    <artifactId>online4edu-dependencies</artifactId>");
        System.out.println("</dependency>");
    }


    private static void generator(String[][] tableNameArr) {

        File javaDir = new File(ProjectProperties.getInstance().getProjectPath() + "/src/main/java");
        if (!javaDir.exists()) {
            javaDir.mkdirs();
        }

        File resourceDir = new File(ProjectProperties.getInstance().getProjectPath() + "/src/main/resources");
        if (!resourceDir.exists()) {
            resourceDir.mkdirs();
        }

        List<TableSign> tableSignList = new ArrayList<>();
        for (String[] tableInfo : tableNameArr) {
            TableSign tableSign = new TableSign();
            for (int j = 0; j < tableInfo.length; ++j) {
                switch (j) {
                    case 0:
                        tableSign.setTableName(tableInfo[j]);
                        break;
                    case 1:
                        tableSign.setDomainName(tableInfo[j]);
                        break;
                    case 2:
                        tableSign.setDescription(tableInfo[j]);
                        break;
                    case 3:
                        tableSign.setPkColumn(tableInfo[j]);
                        break;
                    case 4:
                        tableSign.setPkJavaType(tableInfo[j]);
                        break;
                    default:
                        break;
                }
            }
            tableSignList.add(tableSign);
        }

        doGenerator(tableSignList);
    }


    private static void doGenerator(List<TableSign> tableSignList) {
        ServiceLoader<Generator> serviceLoader = ServiceLoader.load(Generator.class, CodeGeneratorUtil.class.getClassLoader());
        serviceLoader.forEach(o -> o.fileGenerator(tableSignList));
    }
}
