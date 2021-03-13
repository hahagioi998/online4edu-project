package com.online4edu.dependencies.mybatis.generator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 数据表字段信息
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 11:50
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
}
