package com.online4edu.dependencies.utils.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online4edu.dependencies.utils.jackson.JacksonConfig;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 自定义 Jackson Http 消息转换器
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 17:05
 */
@Configuration
public class MessageConvertConfiguration {


    @Bean
    public HttpMessageConverters jsonMessageConverters() {

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonConfig.configureObjectMapper4Jsr310(objectMapper);

        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        //jacksonConverter.setPrettyPrint(true);
        jacksonConverter.setObjectMapper(objectMapper);
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        return new HttpMessageConverters(jacksonConverter);
    }
}
