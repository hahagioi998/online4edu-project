package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.online4edu.dependencies.mybatis.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 抽象 Service
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:18
 */
@Transactional
public class AbstractServiceImpl<T, DTO extends T, PK extends Serializable, M extends Mapper<T, DTO, PK>>
        implements Service<T, DTO, PK> {

    private M mapper;

    @Autowired
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean deleteById(PK id) {
        return false;
    }

    @Override
    public boolean delete(Wrapper<T> queryWrapper) {
        return false;
    }

    @Override
    public T getById(PK id) {
        return null;
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return 0;
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    public <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return null;
    }

    @Override
    public <R> IPage<R> pageEntities(IPage<R> page, Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return null;
    }

    @Override
    public <R> List<R> entityList(Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return null;
    }

    @Override
    public <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column) {
        return null;
    }

    @Override
    public boolean save(T entity) {
        return false;
    }

    @Override
    public void saveBatch(Collection<T> entityList) {

    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean updateById(T entity) {
        return false;
    }

    @Override
    public boolean updateAllColumnById(T entity) {
        return false;
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return false;
    }

    @Override
    public int up(PK id, Class<T> currentModelClass) {
        return 0;
    }

    @Override
    public int down(PK id, Class<T> currentModelClass) {
        return 0;
    }

    @Override
    public int top(PK id, Class<T> currentModelClass) {
        return 0;
    }
}
