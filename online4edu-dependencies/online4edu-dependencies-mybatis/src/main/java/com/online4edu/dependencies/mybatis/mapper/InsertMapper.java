package com.online4edu.dependencies.mybatis.mapper;

import java.io.Serializable;
import java.util.List;

public interface InsertMapper<T, DTO extends T, PK extends Serializable> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    int insert(T entity);

    /**
     * 批量新增数据,自选字段 insert
     *
     * @param entityList 实体对象
     */
    int insertBatchSomeColumn(List<T> entityList);
}
