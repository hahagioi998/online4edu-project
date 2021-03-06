package com.online4edu.dependencies.mybatis.chain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.online4edu.dependencies.mybatis.service.BaseService;

import java.io.Serializable;

/**
 * Lambda 修改 Wrapper
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:02
 */
public class LambdaUpdateWrapperChain<T, DTO extends T, PK extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaUpdateWrapperChain<T, DTO, PK>, LambdaUpdateWrapper<T>>
        implements Update<LambdaUpdateWrapperChain<T, DTO, PK>, SFunction<T, ?>> {

    private final BaseService<T, DTO, PK> baseService;

    public LambdaUpdateWrapperChain(BaseService<T, DTO, PK> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaUpdateWrapper<>();
    }

    @Override
    public LambdaUpdateWrapperChain<T, DTO, PK> set(boolean condition, SFunction<T, ?> column, Object val) {
        wrapperChildren.set(condition, column, val);
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapperChain<T, DTO, PK> setSql(boolean condition, String sql) {
        wrapperChildren.setSql(condition, sql);
        return typedThis;
    }

    @Override
    public String getSqlSet() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSet");
    }

    @SuppressWarnings("unchecked")
    public boolean execute(T entity) {
        return baseService.update(entity, getWrapper());
    }

    @SuppressWarnings("unchecked")
    public boolean execute() {
        return baseService.update(getWrapper());
    }
}