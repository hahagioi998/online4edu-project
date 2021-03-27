package com.online4edu.dependencies.mybatis.chain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.online4edu.dependencies.mybatis.service.BaseService;
import org.apache.ibatis.logging.Log;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Lambda 查询 Wrapper
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:03
 */
@SuppressWarnings("unchecked")
public class LambdaQueryWrapperChain<T, V extends T, Pk extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaQueryWrapperChain<T, V, Pk>, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapperChain<T, V, Pk>, T, SFunction<T, ?>> {

    private final BaseService<T, V, Pk> baseService;

    public LambdaQueryWrapperChain(BaseService<T, V, Pk> baseService) {
        super();
        this.baseService = baseService;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    @Override
    public final LambdaQueryWrapperChain<T, V, Pk> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T, V, Pk> select(Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(predicate);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T, V, Pk> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    public List<T> list() {
        return baseService.list(getWrapper());
    }

    public <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return baseService.listObjs(getWrapper(), mapper);
    }

    public T getOne(Log log) {
        return (T) baseService.getOne(getWrapper(), log);
    }

    public Integer count() {
        return baseService.count(getWrapper());
    }

    public IPage<T> page(IPage<T> page) {
        return baseService.page(page, getWrapper());
    }

    public <R> R getObj(Function<? super Object, R> mapper, Log log) {
        return baseService.getObj((Wrapper<T>) getWrapper(), mapper, log);
    }

    public <R> IPage<R> pageEntities(IPage<R> page, Function<? super T, R> mapper) {
        return baseService.pageEntities(page, getWrapper(), mapper);
    }

    public <R> R entity(Function<? super T, R> mapper, Log log) {
        return baseService.entity((Wrapper<T>) getWrapper(), mapper, log);
    }

    public <R> List<R> entityList(Function<? super T, R> mapper) {
        return baseService.entityList(getWrapper(), mapper);
    }

    public <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return baseService.list2Map(getWrapper(), column);
    }

}