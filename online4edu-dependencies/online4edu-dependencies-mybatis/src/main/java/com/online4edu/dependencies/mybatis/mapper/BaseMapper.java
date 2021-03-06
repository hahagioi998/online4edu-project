package com.online4edu.dependencies.mybatis.mapper;

/**
 * 基础 Mapper
 *
 * <p>
 * 基于 Mybatis Plus 做删减
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:08
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface BaseMapper<T, V extends T>
        extends InsertMapper<T, V>, DeleteMapper<T, V>, UpdateMapper<T, V>, QueryMapper<T, V> {

}
