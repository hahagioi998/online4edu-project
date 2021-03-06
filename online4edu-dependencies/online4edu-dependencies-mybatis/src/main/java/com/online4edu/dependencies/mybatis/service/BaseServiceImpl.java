package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.online4edu.dependencies.mybatis.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽象 Service
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:18
 */
@Slf4j
public abstract class BaseServiceImpl<T, V extends T>
        implements BaseService<T, V> {

    private Class<T> currentModelClass;

    private BaseMapper<T, V> baseMapper;

    @Autowired
    public void setBaseMapper(BaseMapper<T, V> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 获取当前类类型
     *
     * @return 泛型 T
     */
    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        if (Objects.isNull(currentModelClass)) {
            currentModelClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
        }
        return currentModelClass;
    }

    /**
     * 判断是否执行成功
     *
     * @return 返回true表示执行成功, 否则为失败
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * 批量操作 SqlSession
     *
     * @return sql 会话
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放 SqlSession
     *
     * @param session sql 会话
     */
    protected void closeSqlSession(SqlSession session) {
        SqlSessionUtils.closeSqlSession(session, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod sql 方法
     * @return sql
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    // ========================================== Insert =========================================================


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(T entity) {
        if (Objects.nonNull(entity)) {
            return retBool(baseMapper.insert(entity));
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchSomeColumn(List<T> entityList) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            return baseMapper.insertBatchSomeColumn(entityList);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(Collection<T> entityList) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            baseMapper.insertBatchSomeColumn(new ArrayList<>(entityList));
        }
    }

    // ========================================== Update =========================================================


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        return retBool(baseMapper.updateById(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAllColumnById(T entity) {
        if (Objects.nonNull(entity)) {
            return retBool(baseMapper.updateAllColumnById(entity));
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return retBool(baseMapper.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            int i = 0;
            String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
            try (SqlSession batchSqlSession = sqlSessionBatch()) {
                for (T anEntityList : entityList) {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, anEntityList);
                    batchSqlSession.update(sqlStatement, param);
                    if (i >= 1 && i % batchSize == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            }
        }
    }

    // ========================================== Update =========================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrUpdate(T entity) {
        if (Objects.nonNull(entity)) {
            Class<?> clazz = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
            if (Objects.nonNull(tableInfo) && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(clazz, entity, tableInfo.getKeyProperty());

                if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNotNull(idVal)) {
                    log.trace("Primary key id is not set values, the implementation of the [insert] operation.");
                    insert(entity);
                } else {
                    // 尝试更新, 更新成功直接返回. 更新失败继续尝试执行新增操作
                    log.debug("Primary key id is not set values, the implementation of [modification] operations.");

                    return Objects.nonNull(getById((Serializable) idVal)) ? updateById(entity) : insert(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error: Can not execute, Could not find @TableId.");
            }
        }
        return false;
    }

    @Override
    public boolean insertOrUpdateBatch(Collection<T> entityList) {
        // TODO: 2021/3/6 后续实现
        return false;
    }

    @Override
    public int up(Serializable id, Class<T> currentModelClass) {
        return 0;
    }

    @Override
    public int down(Serializable id, Class<T> currentModelClass) {
        return 0;
    }

    @Override
    public int top(Serializable id, Class<T> currentModelClass) {
        return 0;
    }

    // ========================================== DELETE =========================================================


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        if (Objects.nonNull(id)) {
            return retBool(baseMapper.deleteById(id));
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Wrapper<T> queryWrapper) {
        return retBool(baseMapper.delete(queryWrapper));
    }

    // ========================================== Select =========================================================

    @Override
    public T getById(Serializable id) {
        if (Objects.nonNull(id)) {
            return baseMapper.selectById(id);
        }
        return null;
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> IPage<R> pageEntities(IPage page, Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return page(page, wrapper).convert(mapper);
    }

    @Override
    public <R> List<R> entityList(Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return list(wrapper).stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column) {
        return list2Map(list(wrapper), column);
    }

    @SuppressWarnings("unchecked")
    private <K> Map<K, T> list2Map(List<T> list, SFunction<T, K> column) {
        if (list == null) {
            return Collections.emptyMap();
        }
        Map<K, T> map = new LinkedHashMap<>(list.size());
        for (T t : list) {
            Field field = ReflectionUtils.findField(t.getClass(), getColumn(LambdaUtils.resolve(column)));
            if (Objects.isNull(field)) {
                continue;
            }
            ReflectionUtils.makeAccessible(field);
            Object fieldValue = ReflectionUtils.getField(field, t);
            map.put((K) fieldValue, t);
        }
        return map;
    }

    private String getColumn(SerializedLambda lambda) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.resolveFieldName(lambda.getImplMethodName());
    }
}
