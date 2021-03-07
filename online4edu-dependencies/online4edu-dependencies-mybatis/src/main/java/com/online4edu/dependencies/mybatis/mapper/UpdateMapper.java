package com.online4edu.dependencies.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 基础 Mapper
 *
 * <p>
 * 基于 Mybatis Plus 做删减
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/07 16:52
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface UpdateMapper<T, V extends T> {

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    int updateById(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

    /**
     * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     *
     * @param entity 实体对象
     */
    int alwaysUpdateSomeColumnById(T entity);


    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return 更新结果
     */
    int updateAllColumnById(@Param(Constants.ENTITY) T entity);
}
