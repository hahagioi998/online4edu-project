package com.online4edu.dependencies.mybatis.service;

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
public interface SaveService<T, V extends T>
        extends InsertService<T, V>, UpdateService<T, V> {

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return 执行成功返回true, 否则false
     * @see com.baomidou.mybatisplus.annotation.TableId
     */
    boolean insertOrUpdate(T entity);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @return 保存成功返回true, 否则返回false
     */
    boolean insertOrUpdateBatch(Collection<T> entityList);


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
    int up(Serializable id, Class<T> currentModelClass);

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
    int down(Serializable id, Class<T> currentModelClass);

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
    int top(Serializable id, Class<T> currentModelClass);
}
