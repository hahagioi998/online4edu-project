package com.online4edu.dependencies.mybatis.generator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库链接配置信息
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 12:07
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JdbcProperties {

    private String author;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPwd;

    private JdbcProperties() {
    }

    private static final JdbcProperties INSTANCE = new JdbcProperties();

    public static JdbcProperties getInstance() {
        return INSTANCE;
    }

    public void init(String author, String jdbcUrl, String jdbcUser, String jdbcPwd) {
        this.author = author;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPwd = jdbcPwd;
    }

    public String getJdbcUrl() {

        char segmentation = '&';
        if (!jdbcUrl.contains("?")) {
            segmentation = '?';
        }
        if (!jdbcUrl.contains("tinyInt1isBit")) {
            jdbcUrl += segmentation + "tinyInt1isBit=false";
        }

        return jdbcUrl;
    }
}