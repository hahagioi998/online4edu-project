package com.online4edu.app.gateway.config;

import com.online4edu.dependencies.mybatis.spring.MyBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 配置类
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 21:39
 */
@Configuration
@Import(MyBatisPlusConfig.class)
@MapperScan("com.online4edu.app.*.mapper")
public class AppConfig {
}
