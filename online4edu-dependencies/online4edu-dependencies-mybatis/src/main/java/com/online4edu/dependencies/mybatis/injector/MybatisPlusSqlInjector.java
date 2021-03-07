package com.online4edu.dependencies.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * MybatisPlusSql注入器
 * </p>
 *
 * @author Caratacus
 */
public class MybatisPlusSqlInjector extends AbstractSqlInjector {


    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteById(),
                new DeleteByMap(),
                new Update(),
                new UpdateById(),
                new UpdateAllColumnById(),
                new SelectById(),
                new SelectOne(),
                new SelectList(),
                new SelectCount(),
                new SelectObjs(),
                new SelectPage(),
                new SelectBatchByIds()
        ).collect(Collectors.toList());
    }
}