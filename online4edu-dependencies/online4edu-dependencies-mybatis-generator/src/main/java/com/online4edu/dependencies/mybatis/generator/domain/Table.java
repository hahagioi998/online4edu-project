package com.online4edu.dependencies.mybatis.generator.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据表信息
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-07-03 20:59
 */
public class Table implements Serializable {

    private static final Long serialVersionUID = -1L;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
