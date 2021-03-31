package com.online4edu.dependencies.mybatis.generator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 项目基础信息
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 12:08
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProjectProperties {

    private String projectPath;
    private String javaPath;
    private String resourcesPath;


    private String projectPackage;
    private String packageDomain;
    private String packageVO;
    private String packageMapper;
    private String packageService;
    private String packageServiceImpl;
    private String packageWeb;

    private String stripPrefix;

    private static final String JAVA_DIR = "/src/main/java";
    private static final String RESOURCES_DIR = "/src/main/resources";

    /**
     * Mapper 基础继承接口
     */
    public static String BASE_MAPPER_INTERFACE = "com.online4edu.dependencies.mybatis.mapper.BaseMapper";

    private ProjectProperties() {
    }

    private static final ProjectProperties INSTANCE = new ProjectProperties();

    public static ProjectProperties getInstance() {
        return INSTANCE;
    }

    public void init(String projectPath, String projectPackage, String stripPrefix) {
        this.projectPath = projectPath;
        this.javaPath = projectPath + JAVA_DIR;
        this.resourcesPath = projectPath + RESOURCES_DIR;

        this.projectPackage = projectPackage;
        this.packageVO = projectPackage + ".vo";
        this.packageDomain = projectPackage + ".domain";
        this.packageMapper = projectPackage + ".mapper";
        this.packageService = projectPackage + ".service";
        this.packageServiceImpl = this.packageService + ".impl";
        this.packageWeb = projectPackage + ".web";

        this.stripPrefix = stripPrefix;
    }
}