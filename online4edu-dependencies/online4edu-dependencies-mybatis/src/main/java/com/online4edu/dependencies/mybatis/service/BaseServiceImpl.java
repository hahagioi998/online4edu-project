package com.online4edu.dependencies.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.online4edu.dependencies.mybatis.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 抽象 Service
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/06 00:18
 */
@Slf4j
public class BaseServiceImpl<T, DTO extends T, PK extends Serializable, M extends BaseMapper<T, DTO, PK>>
        implements BaseService<T, DTO, PK> {

    private M mapper;

    private Class<T> currentModelClass;

    @Autowired
    public void setMapper(M mapper) {
        this.mapper = mapper;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(T entity) {
        return retBool(mapper.insert(entity));
    }

    @Override
    public void saveBatch(Collection<T> entityList) {

        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        // TODO: 2021/3/6 增加批处理
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {


        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        return retBool(mapper.updateById(entity));
    }

    @Override
    public boolean updateAllColumnById(T entity) {
        // TODO: 2021/3/6
        return false;
    }

    @Override
    public boolean update(Wrapper<T> updateWrapper) {
        // TODO: 2021/3/6
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return retBool(mapper.update(entity, updateWrapper));
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(T entity) {
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

                    return Objects.nonNull(getById((PK) idVal)) ? updateById(entity) : insert(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error: Can not execute, Could not find @TableId.");
            }
        }
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
