package com.online4edu.dependencies.mybatis.generator.core;

import com.online4edu.dependencies.mybatis.generator.config.SimpleJavaTypeResolverImpl;
import com.online4edu.dependencies.mybatis.generator.domain.JdbcProperties;
import com.online4edu.dependencies.mybatis.generator.domain.ProjectProperties;
import com.online4edu.dependencies.mybatis.generator.domain.TableSign;
import com.online4edu.dependencies.mybatis.generator.plugin.MapperPluginAdapter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成实体类和Mapper文件
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/30 21:04
 */
public class DomainAndMapperGenerator implements Generator {

    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    @Override
    public void fileGenerator(List<TableSign> tableSignList) {

        Context context = initContext();

        // jdbc 连接
        initJdbcConnection(context);

        // jdbc 类型映射
        injectJavaTypeConfiguration(context);

        // 增加 mybatis 插件
        injectPluginConfiguration(context);

        // Model 文件生成配置
        generatorModelFile(context);

        // Mapper 文件生成配置
        generatorMapperFile(context);

        // 数据表配置
        setTables(context, tableSignList);

        // 开始生成
        start(context);
    }


    /**
     * 初始化上下文
     */
    private Context initContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        return context;
    }


    /**
     * 设置 Jdbc 连接
     */
    private void initJdbcConnection(Context context) {
        // JDBC 连接配置
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JdbcProperties.getInstance().getJdbcUrl());
        jdbcConnectionConfiguration.setUserId(JdbcProperties.getInstance().getJdbcUser());
        jdbcConnectionConfiguration.setPassword(JdbcProperties.getInstance().getJdbcPwd());
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
    }


    /**
     * 设置 Jdbc -> Java 类型映射插件
     */
    private void injectJavaTypeConfiguration(Context context) {
        // 扩展 JDBC => Java 类型映射
        JavaTypeResolverConfiguration resolverConfiguration = new JavaTypeResolverConfiguration();
        resolverConfiguration.setConfigurationType(SimpleJavaTypeResolverImpl.class.getName());
        context.setJavaTypeResolverConfiguration(resolverConfiguration);
    }


    /**
     * 注入自定义扩展插件
     *
     * @see ProjectProperties#BASE_MAPPER_INTERFACE
     */
    private void injectPluginConfiguration(Context context) {

        // 使用 mybatisPlus 插件, 设置 mapper 接口继承自定义的 BaseMapper 接口
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(MapperPluginAdapter.class.getName());
        pluginConfiguration.addProperty("mappers", ProjectProperties.BASE_MAPPER_INTERFACE);
        context.addPluginConfiguration(pluginConfiguration);

        // 序列化
        PluginConfiguration serializablePlugin = new PluginConfiguration();
        serializablePlugin.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        /*serializablePlugin.addProperty("vo", vo);*/
        context.addPluginConfiguration(serializablePlugin);
    }


    /**
     * 配置生成实体类 Model 文件
     */
    private void generatorModelFile(Context context) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(ProjectProperties.getInstance().getJavaPath());
        javaModelGeneratorConfiguration.setTargetPackage(ProjectProperties.getInstance().getPackageDomain());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
    }


    /**
     * 配置生成 Mapper 文件
     */
    private void generatorMapperFile(Context context) {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(ProjectProperties.getInstance().getResourcesPath());
        sqlMapGeneratorConfiguration.setTargetPackage("mybatis/mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(ProjectProperties.getInstance().getJavaPath());
        javaClientGeneratorConfiguration.setTargetPackage(ProjectProperties.getInstance().getPackageMapper());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }


    /**
     * 数据表配置
     */
    private void setTables(Context context, List<TableSign> tableSignList) {
        for (TableSign tableSign : tableSignList) {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableSign.getTableName());
            tableConfiguration.setDomainObjectName(tableSign.getDomainName());
            // 主键
            tableConfiguration.setGeneratedKey(new GeneratedKey(tableSign.getPkColumn(), "Mysql", true, null));
            context.addTableConfiguration(tableConfiguration);
        }
    }


    /**
     * 开始生成文件
     */
    private void start(Context context) {

        MyBatisGenerator generator;
        ArrayList<String> warnings = new ArrayList<>();
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            DefaultShellCallback callback = new DefaultShellCallback(true);
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Domain和Mapper失败", e);
        }

        String domainName = ProjectProperties.getInstance().getPackageDomain();
        if (!generator.getGeneratedJavaFiles().isEmpty() && !generator.getGeneratedXmlFiles().isEmpty()) {
            System.out.println(domainName + ".java 生成成功");
            System.out.println(domainName + "Mapper.java 生成成功");
            System.out.println(domainName + "Mapper.xml 生成成功");
        } else {
            throw new RuntimeException("生成Domain和Mapper失败：" + warnings);
        }
    }
}
