package com.online4edu.dependencies.mybatis.generator.domain;

import java.io.Serializable;

/**
 * 数据表字段信息
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-07-03 21:55
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 6532448600416890162L;

    public Column() {
    }

    public Column(String name, String type, String key, String isNullable, String comment) {
        this.name = name;
        this.type = type;
        this.key = key;
        this.isNullable = isNullable;
        this.comment = comment;
    }

    /** 字段名 */
    private String name;

    /** 字段数据类型 */
    private String type;

    /** key */
    private String key;

    /** 是否可以为 NULL */
    private String isNullable;

    /** 字段描述 */
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
