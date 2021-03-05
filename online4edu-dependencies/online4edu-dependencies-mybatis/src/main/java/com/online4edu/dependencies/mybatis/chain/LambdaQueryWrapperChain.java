package com.online4edu.dependencies.mybatis.chain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.online4edu.dependencies.mybatis.service.Service;
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
public class LambdaQueryWrapperChain<T, DTO extends T, PK extends Serializable>
        extends AbstractChainWrapper<T, SFunction<T, ?>, LambdaQueryWrapperChain<T, DTO, PK>, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapperChain<T, DTO, PK>, T, SFunction<T, ?>> {

    private final Service<T, DTO, PK> service;

    public LambdaQueryWrapperChain(Service<T, DTO, PK> service) {
        super();
        this.service = service;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    @Override
    public final LambdaQueryWrapperChain<T, DTO, PK> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T, DTO, PK> select(Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(predicate);
        return typedThis;
    }

    @Override
    public LambdaQueryWrapperChain<T, DTO, PK> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    public List<T> list() {
        return service.list(getWrapper());
    }

    public <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return service.listObjs(getWrapper(), mapper);
    }

    public T getOne(Log log) {
        return (T) service.getOne(getWrapper(), log);
    }

    public Integer count() {
        return service.count(getWrapper());
    }

    public IPage<T> page(IPage<T> page) {
        return service.page(page, getWrapper());
    }

    public <R> R getObj(Function<? super Object, R> mapper, Log log) {
        return service.getObj((Wrapper<T>) getWrapper(), mapper, log);
    }

    public <R> IPage<R> pageEntities(IPage<R> page, Function<? super T, R> mapper) {
        return service.pageEntities(page, getWrapper(), mapper);
    }

    public <R> R entity(Function<? super T, R> mapper, Log log) {
        return service.entity((Wrapper<T>) getWrapper(), mapper, log);
    }

    public <R> List<R> entityList(Function<? super T, R> mapper) {
        return service.entityList(getWrapper(), mapper);
    }

    public <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return service.list2Map(getWrapper(), column);
    }

}