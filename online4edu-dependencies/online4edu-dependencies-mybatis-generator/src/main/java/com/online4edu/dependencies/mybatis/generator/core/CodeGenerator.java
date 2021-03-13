package com.online4edu.dependencies.mybatis.generator.core;

import com.google.common.base.CaseFormat;
import com.online4edu.dependencies.mybatis.generator.config.SimpleJavaTypeResolverImpl;
import com.online4edu.dependencies.mybatis.generator.plugin.MapperPluginAdapter;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO、Model、DTO、Mapper 文件生成类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 18/10/2018 13:07
 * @see SimpleJavaTypeResolverImpl
 */
public class CodeGenerator {
    /**
     * java文件目录
     */
    private static final String JAVA_PATH = "/src/main/java";
    /**
     * 资源文件目录
     */
    private static final String RESOURCES_PATH = "/src/main/resources";
    /**
     * JDBC驱动
     */
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static String vo;
    private static String service;
    private static String serviceImpl;
    private static final String DATE = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date());

    /**
     * 获取 DTO、Service、Impl 文件包路径
     *
     * @param tableNameArr 数据表二维数组
     * @param projectPath  项目生成地址
     */
    public CodeGenerator(String[][] tableNameArr, String projectPath) {

        ProjectProperties instance = ProjectProperties.getInstance();
        vo = packageConvertPath(instance.getVo());
        service = packageConvertPath(instance.getService());
        serviceImpl = packageConvertPath(instance.getServiceImpl());

        genCode(tableNameArr, projectPath);
    }

    /**
     * 获取数据表名
     *
     * @param tableNameArr 数据表二维数组
     * @param projectPath  项目生成地址
     */
    private static void genCode(String[][] tableNameArr, String projectPath) {

        File javaDir = new File(projectPath + "/src/main/java");
        if (!javaDir.exists()) {
            javaDir.mkdirs();
        }

        File resourceDir = new File(projectPath + "/src/main/resources");
        if (!resourceDir.exists()) {
            resourceDir.mkdirs();
        }

        String tableName = null, domainName = null, description = null, pk = null, pkDataType = null;

        for (String[] tableInfo : tableNameArr) {
            for (int j = 0; j < tableInfo.length; ++j) {
                switch (j) {
                    case 0:
                        tableName = tableInfo[j];
                        break;
                    case 1:
                        domainName = tableInfo[j];
                        break;
                    case 2:
                        description = tableInfo[j];
                        break;
                    case 3:
                        pk = tableInfo[j];
                        break;
                    case 4:
                        pkDataType = tableInfo[j];
                        break;
                    default:
                        break;
                }
            }

            CodeGenerator.genCodeByCustomDomainName(tableName, domainName, description, pk, pkDataType, projectPath);
        }

    }

    /**
     * 文件生成
     *
     * @param tableName   表名
     * @param domainName  实体类
     * @param description 描述
     * @param pk          主键
     * @param pkDataType  主键类型
     * @param projectPath 项目生成地址
     */
    private static void genCodeByCustomDomainName(
            String tableName, String domainName, String description,
            String pk, String pkDataType, String projectPath) {


        CodeGenerator.genDomainAndMapper(tableName, domainName, pk, projectPath);
//        CodeGenerator.genMyBatisConfig(projectPath);
        CodeGenerator.genService(tableName, domainName, description, pkDataType, projectPath);
        CodeGenerator.genVO(tableName, domainName, description, projectPath);
//        String newName = projectPath.substring(projectPath.indexOf("\\") + 1);
//		CodeGenerator.genPom(newName, projectPath);
    }

    /**
     * Domain、Mapper 文件生成
     *
     * @param tableName   表名
     * @param domainName  实体类名
     * @param pk          实体类主键
     * @param projectPath 项目生成地址
     */
    private static void genDomainAndMapper(
            String tableName, String domainName, String pk, String projectPath) {

        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");

        // 扩展 JDBC => Java 类型映射
        JavaTypeResolverConfiguration resolverConfiguration = new JavaTypeResolverConfiguration();
        resolverConfiguration.setConfigurationType(SimpleJavaTypeResolverImpl.class.getName());
        context.setJavaTypeResolverConfiguration(resolverConfiguration);

        // JDBC 连接配置
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JdbcProperties.getInstance().getJdbcUrl());
        jdbcConnectionConfiguration.setUserId(JdbcProperties.getInstance().getJdbcUser());
        jdbcConnectionConfiguration.setPassword(JdbcProperties.getInstance().getJdbcPwd());
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // 使用 tkMapper 插件,生成 mapper 接口继承 ProjectConstant.MAPPER_INTERFACE_REFERENCE 定义接口
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(MapperPluginAdapter.class.getName());
        pluginConfiguration.addProperty("mappers", ProjectProperties.BASE_MAPPER_INTERFACE);
        context.addPluginConfiguration(pluginConfiguration);

        // 增加 mybatis 插件
        PluginConfiguration serializablePlugin = new PluginConfiguration();
        serializablePlugin.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
//        serializablePlugin.addProperty("vo", vo);
        context.addPluginConfiguration(serializablePlugin);

        // Model 文件生成配置
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(projectPath + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(ProjectProperties.getInstance().getDomain());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // Mapper 文件生成配置
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(projectPath + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mybatis/mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(projectPath + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(ProjectProperties.getInstance().getMapper());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // 数据表配置
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(domainName)) {
            tableConfiguration.setDomainObjectName(domainName);
        }

        tableConfiguration.setGeneratedKey(new GeneratedKey(pk, "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        ArrayList<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception var21) {
            throw new RuntimeException("生成Domain和Mapper失败", var21);
        }

        if (!generator.getGeneratedJavaFiles().isEmpty() && !generator.getGeneratedXmlFiles().isEmpty()) {
            if (StringUtils.isEmpty(domainName)) {
                domainName = tableNameConvertUpperCamel(tableName);
            }

            System.out.println(domainName + ".java 生成成功");
            System.out.println(domainName + "Mapper.java 生成成功");
            System.out.println(domainName + "Mapper.xml 生成成功");
        } else {
            throw new RuntimeException("生成Domain和Mapper失败：" + warnings);
        }
    }


    private static void genMyBatisConfig(String projectPath) {
        try {
            Map<String, Object> data = new HashMap<String, Object>(16);
            File file = new File(projectPath + RESOURCES_PATH + "/mybatis/mybatis-config.xml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            getConfiguration().getTemplate("mybatis-config.ftl").process(data, new FileWriter(file));
            System.out.println("mybatis-config.xml 生成成功");
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("mybatis-config.xml 生成失败", e);
        }

    }


    /**
     * Service、Impl 接口文件生成
     *
     * @param tableName   表名
     * @param domainName  实体类
     * @param description 描述
     * @param pkDataType  主键类型
     * @param projectPath 项目生成地址
     */
    private static void genService(String tableName, String domainName, String description, String pkDataType, String projectPath) {
        try {
            Map<String, Object> data = new HashMap<String, Object>(16);
            String desc = StringUtils.isEmpty(description) ? "" : description;
            data.put("description", desc);
            data.put("date", DATE);
            data.put("author", JdbcProperties.getInstance().getAuthor());
            data.put("pkDataType", pkDataType);
            String domainNameUpperCamel = StringUtils.isEmpty(domainName) ? tableNameConvertUpperCamel(tableName) : domainName;
            data.put("domainNameUpperCamel", domainNameUpperCamel);
            data.put("domainNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", ProjectProperties.getInstance().getProjectPackage());
            File file = new File(projectPath + JAVA_PATH + service + domainNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            getConfiguration().getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(domainNameUpperCamel + "Service.java 生成成功");
            File file1 = new File(projectPath + JAVA_PATH + serviceImpl + domainNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }

            getConfiguration().getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
            System.out.println(domainNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception var14) {
            throw new RuntimeException("生成Service失败", var14);
        }
    }

    /**
     * DTO 实体类扩展文件生成
     *
     * @param tableName   表名
     * @param domainName  实体类
     * @param description 描述
     * @param projectPath 项目生成地址
     */
    private static void genVO(String tableName, String domainName, String description, String projectPath) {
        try {
            Map<String, Object> data = new HashMap<String, Object>(16);
            String desc = StringUtils.isEmpty(description) ? "" : description;
            data.put("description", desc);
            data.put("date", DATE);
            data.put("author", JdbcProperties.getInstance().getAuthor());
            String domainNameUpperCamel = StringUtils.isEmpty(domainName) ? tableNameConvertUpperCamel(tableName) : domainName;
            data.put("baseRequestMapping", domainNameConvertMappingPath(domainNameUpperCamel));
            data.put("domainNameUpperCamel", domainNameUpperCamel);
            data.put("domainNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, domainNameUpperCamel));
            data.put("basePackage", ProjectProperties.getInstance().getProjectPackage());
            File file = new File(projectPath + JAVA_PATH + vo + domainNameUpperCamel + "VO.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            getConfiguration().getTemplate("vo.ftl").process(data, new FileWriter(file));
            System.out.println(domainNameUpperCamel + "DTO.java 生成成功");
        } catch (Exception var12) {
            throw new RuntimeException("生成DTO失败", var12);
        }
    }

    /**
     * POM 文件生成
     */
    private static void genPom(String projectName, String projectPath) {
        try {
            Map<String, Object> data = new HashMap<String, Object>(3);
            data.put("projectName", projectName);
            data.put("artifactId", "${project.artifactId}");
            data.put("version", "${project.version}");
            File file = new File(projectPath + "\\pom.xml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            getConfiguration().getTemplate("pom.ftl").process(data, new FileWriter(file));
            System.out.println("pom.xml 生成成功");
        } catch (Exception var6) {
            throw new RuntimeException("生成pom.xml失败", var6);
        }
    }

    /**
     * freemarker 配置
     */
    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        cfg.setClassLoaderForTemplateLoading(CodeGenerator.class.getClassLoader(), "template/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    /**
     * 数据表小写驼峰转换
     */
    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    /**
     * 数据表大写驼峰转换
     */
    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    /**
     *
     */
    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();
        return "/" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
    }

    /**
     *
     */
    private static String domainNameConvertMappingPath(String domainName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, domainName);
        return tableNameConvertMappingPath(tableName);
    }

    /**
     * 包级转物理地址
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}