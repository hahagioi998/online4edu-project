package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.logging.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 基础 QueryService
 *
 * <p>
 * 基于 Mybatis-plus 做 Service 层扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/05 18:57
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface QueryService<T, DTO extends T, PK extends Serializable> {

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return entity
     */
    T getById(PK id);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @param log          mybatis 日志记录器
     * @return entity T
     * @see org.apache.ibatis.logging.LogFactory
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    default T getOne(Wrapper<T> queryWrapper, Log log) {
        return SqlHelper.getObject(log, list(queryWrapper));
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @param mapper       Mapper
     * @param log          日志记录器
     * @return 自定义entity R
     * @see org.apache.ibatis.logging.LogFactory
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    default <R> R getObj(Wrapper<T> queryWrapper, Function<? super Object, R> mapper, Log log) {
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

    /**
     * 根据 Wrapper 条件, 查询总记录数
     *
     * @return 数量
     */
    default int count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件,查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 数量
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    int count(Wrapper<T> queryWrapper);

    /**
     * 查询列表
     *
     * @return list entity T
     */
    default List<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类
     * @return list entity T
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * 翻页查询
     *
     * @param page 翻页对象
     * @return page entity T
     */
    default IPage<T> page(IPage<T> page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件,查询全部记录
     *
     * @param mapper 自定义mapper
     * @return list
     */
    default <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类
     * @return page entity T
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类
     * @param mapper       自定义mapper
     * @return list
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */

    <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper);

    /**
     * 翻页查询自定义对象
     *
     * @param page   翻页对象
     * @param mapper 自定义mapper
     * @return page
     */
    default <R> IPage<R> pageEntities(IPage<R> page, Function<? super T, R> mapper) {
        return pageEntities(page, Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 翻页查询自定义对象
     *
     * @param page    翻页对象
     * @param wrapper {@link Wrapper}
     * @param mapper  自定义mapper
     * @return page
     */
    <R> IPage<R> pageEntities(IPage<R> page, Wrapper<T> wrapper, Function<? super T, R> mapper);

    /**
     * 根据 Wrapper，查询一条自定义对象记录
     *
     * @param wrapper {@link Wrapper}
     * @param mapper  mapper
     * @return R
     */
    default <R> R entity(Wrapper<T> wrapper, Function<? super T, R> mapper, Log log) {
        return SqlHelper.getObject(log, entityList(wrapper, mapper));
    }

    /**
     * 查询自定义对象列表
     *
     * @param mapper mapper
     * @return list
     */
    default <R> List<R> entityList(Function<? super T, R> mapper) {
        return entityList(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 查询自定义对象列表
     *
     * @param wrapper {@link Wrapper}
     * @param mapper  mapper
     * @return list
     */
    <R> List<R> entityList(Wrapper<T> wrapper, Function<? super T, R> mapper);

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     *
     * @param column list中对象的属性,作为键值
     * @return 转换后的map
     */
    default <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return list2Map(Wrappers.emptyWrapper(), column);
    }

    /**
     * 查询list,使用list中对象的某个属性做键值,转换成map
     *
     * @param wrapper 条件
     * @param column  list中对象的属性,作为键值
     * @return 转换后的map
     */
    <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column);
}
