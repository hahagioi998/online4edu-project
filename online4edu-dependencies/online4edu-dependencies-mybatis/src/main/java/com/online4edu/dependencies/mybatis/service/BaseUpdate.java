package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基础 UpdateService
 *
 * <p>
 * 基于 Mybatis-plus 做 Service 层扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/05 18:57
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface BaseUpdate<T, DTO extends T, PK extends Serializable> {

    /**
     * 批量大小
     */
    int BATCH_SIZE = 1024;

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return 保存成功返回true, 否则返回false
     */
    boolean save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    void saveBatch(Collection<T> entityList);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @return 保存成功返回true, 否则返回false
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

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
     * @return 修改成功返回true, 否则返回false
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, BATCH_SIZE);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     * @return 修改成功返回true, 否则返回false
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return 执行成功返回true, 否则false
     * @see com.baomidou.mybatisplus.annotation.TableId
     */
    boolean saveOrUpdate(T entity);


    /**
     * 上移
     *
     * <p>
     * 业务自定方法, 表字段满足sequence-序列 id-主键. 慎用,有sql注入风险.
     *
     * @param id                主键值
     * @param currentModelClass 必须传入带有表名主键的实体类 如@TableName(<TableName>)
     * @return 成功执行行数
     * @see com.baomidou.mybatisplus.annotation.TableName
     */
    int up(PK id, Class<T> currentModelClass);

    /**
     * 下移
     *
     * <p>
     * 业务自定方法, 表字段满足sequence-序列 id-主键. 慎用,有sql注入风险.
     *
     * @param id                主键值
     * @param currentModelClass 必须传入带有表名主键的实体类 如@TableName(<TableName>)
     * @return 成功执行行数
     * @see com.baomidou.mybatisplus.annotation.TableName
     */
    int down(PK id, Class<T> currentModelClass);

    /**
     * 置顶
     *
     * <p>
     * 业务自定方法, 表字段满足sequence-序列 id-主键. 慎用,有sql注入风险.
     *
     * @param id                主键值
     * @param currentModelClass 必须传入带有表名主键的实体类 如@TableName(<TableName>)
     * @return 成功执行行数
     * @see com.baomidou.mybatisplus.annotation.TableName
     */
    int top(PK id, Class<T> currentModelClass);
}
