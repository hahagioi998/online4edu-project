package com.online4edu.dependencies.mybatis.chain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.online4edu.dependencies.mybatis.service.BaseService;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Lambda 删除 Wrapper
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:04
 */
public class LambdaDeleteWrapperChain<T, DTO extends T, PK extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaDeleteWrapperChain<T, DTO, PK>, LambdaQueryWrapper<T>>
        implements Query<LambdaDeleteWrapperChain<T, DTO, PK>, T, SFunction<T, ?>> {

    private final BaseService<T, DTO, PK> baseService;

    public LambdaDeleteWrapperChain(BaseService<T, DTO, PK> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    @Override
    public final LambdaDeleteWrapperChain<T, DTO, PK> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public LambdaDeleteWrapperChain<T, DTO, PK> select(Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(predicate);
        return typedThis;
    }

    @Override
    public LambdaDeleteWrapperChain<T, DTO, PK> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @SuppressWarnings("unchecked")
    public boolean execute() {
        return baseService.delete(getWrapper());
    }

}