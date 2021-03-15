package com.online4edu.dependencies.mybatis.spring;

import com.online4edu.dependencies.mybatis.mapper.BaseMapper;
import com.online4edu.dependencies.mybatis.service.BaseService;
import com.online4edu.dependencies.mybatis.service.BaseServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * Bean 工厂后置处理器, 根据 Bean 类型自动完成依赖注入.
 * <p>
 * 将该后置处理器注入 Spring, 不需要在 {@link BaseServiceImpl#setBaseMapper(BaseMapper)}
 * 上增加 {@link org.springframework.beans.factory.annotation.Autowired} 注解
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/15 10:12
 * @see BaseServiceImpl#setBaseMapper(BaseMapper)
 */
public class MyBatisPlusBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(BaseService.class);
        for (String beanName : beanNamesForType) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition(beanName);
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }
}
