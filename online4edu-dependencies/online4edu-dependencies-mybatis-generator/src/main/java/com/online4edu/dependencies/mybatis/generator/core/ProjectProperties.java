package com.online4edu.dependencies.mybatis.generator.core;

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

    private String vo;
    private String projectPackage;
    private String domain;
    private String mapper;
    private String service;
    private String serviceImpl;

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

    public void init(String projectPackage) {
        this.projectPackage = projectPackage;
        this.vo = projectPackage + ".vo";
        this.domain = projectPackage + ".domain";
        this.mapper = projectPackage + ".mapper";
        this.service = projectPackage + ".service";
        this.serviceImpl = this.service + ".impl";
    }
}