package com.online4edu.dependencies.mybatis.mapper;

import java.io.Serializable;

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
public interface BaseMapper<T, DTO extends T, PK extends Serializable>
        extends InsertMapper<T, DTO, PK>, DeleteMapper<T, DTO, PK>, UpdateMapper<T, DTO, PK>, QueryMapper<T, DTO, PK> {

}
