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
public class LambdaUpdateWrapperChain<T, V extends T, Pk extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaUpdateWrapperChain<T, V, Pk>, LambdaUpdateWrapper<T>>
        implements Update<LambdaUpdateWrapperChain<T, V, Pk>, SFunction<T, ?>> {

    private final BaseService<T, V, Pk> baseService;

    public LambdaUpdateWrapperChain(BaseService<T, V, Pk> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaUpdateWrapper<>();
    }

    @Override
    public LambdaUpdateWrapperChain<T, V, Pk> set(boolean condition, SFunction<T, ?> column, Object val) {
        wrapperChildren.set(condition, column, val);
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapperChain<T, V, Pk> setSql(boolean condition, String sql) {
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