package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;

public interface UpdateService<T, V extends T, Pk extends Serializable> {

    /**
     * 批量大小
     */
    int BATCH_SIZE = 1024;

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     * @return 修改成功返回true, 否则返回false
     */
    boolean updateById(T entity);

    /**
     * 根据 ID 全部修改
     *
     * @param entity 实体对象
     * @return 修改成功返回true, 否则返回false
     */
    boolean updateAllColumnById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param updateWrapper 实体对象封装操作类
     * @return 修改成功返回true, 否则返回false
     * @see com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
     */
    default boolean update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类
     * @return 修改成功返回true, 否则返回false
     * @see com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    default void updateBatchById(Collection<T> entityList) {
        updateBatchById(entityList, BATCH_SIZE);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    default void updateBatchById(Collection<T> entityList, int batchSize) {
        throw new UnsupportedOperationException("该功能当前未做实现, 不支持使用");
    }
}
