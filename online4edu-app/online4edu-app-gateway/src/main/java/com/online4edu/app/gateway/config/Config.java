package com.online4edu.app.gateway.config;

import com.online4edu.dependencies.mybatis.MyBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 21:39
 */
@Configuration
@MapperScan("com.online4edu.app.*.mapper")
@ComponentScan(basePackageClasses = MyBatisPlusConfig.class)
public class Config {
}
