package com.online4edu.dependencies.mybatis.generator.core;

/**
 * 常量类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 18/10/2018 13:04
 */
public class ProjectConstant {

    private String vo;
    private String pkg;
    private String domain;
    private String mapper;
    private String service;
    private String serviceImpl;

    /**
     * Mapper 基础继承接口
     */
    public static String MAPPER_INTERFACE_REFERENCE = "com.online4edu.dependencies.mybatis.mapper.BaseMapper";

    public ProjectConstant(String pkg) {
        this.pkg = pkg;
        this.vo = pkg + ".vo";
        this.domain = pkg + ".domain";
        this.mapper = pkg + ".mapper";
        this.service = pkg + ".service";
        this.serviceImpl = this.service + ".impl";
    }

    public String getPkg() {
        return this.pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMapper() {
        return this.mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceImpl() {
        return this.serviceImpl;
    }

    public void setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getVo() {
        return this.vo;
    }

    public void setVo(String vo) {
        this.vo = vo;
    }
}