package com.online4edu.dependencies.mybatis.generator;

import com.online4edu.dependencies.mybatis.generator.util.CodeUtil;

public class GenerateMain {

    private static final String JDBC_URL = "jdbc:mysql://127.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&tinyInt1isBit=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "admin123";
    private static final String AUTHOR = "zhangshilin <br > zhang.shilin@mail.com";
    private static final String PROJECT_PATH = "/Users/mingrn97/Downloads/generator";
    private static final String PROJECT_PACKAGE = "com.yilutong.crm";

    public static void main(String[] args) {
        String[][] tableNames = {
                {"sys_administrative_region", "", "测试示例", "id", "Long"},
        };

        CodeUtil.create(tableNames, AUTHOR, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, PROJECT_PATH, PROJECT_PACKAGE);
    }
}