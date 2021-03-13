package com.online4edu.dependencies.mybatis.generator.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 数据表信息
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 11:50
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Table implements Serializable {

    private static final long serialVersionUID = -7497224466254008135L;

    /** 表名 */
    private String name;

    /** 表描述信息 */
    private String comment;

    /** 表创建时间 */
    private Date createDate;

    public Table() {
    }

    public Table(String name, String comment, Date createDate) {
        this.name = name;
        this.comment = comment;
        this.createDate = createDate;
    }
}
