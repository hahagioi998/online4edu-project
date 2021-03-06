package com.online4edu.dependencies.mybatis.service;

import java.util.Collection;
import java.util.List;

public interface InsertService<T, V extends T> {

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return 保存成功返回true, 否则返回false
     */
    boolean insert(T entity);

    /**
     * 批量新增数据,自选字段 insert
     *
     * @param entityList 实体对象
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    void insertBatch(Collection<T> entityList);
}
