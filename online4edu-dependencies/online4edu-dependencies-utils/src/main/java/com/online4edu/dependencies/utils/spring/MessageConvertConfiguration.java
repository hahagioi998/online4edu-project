package com.online4edu.dependencies.utils.spring;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.online4edu.dependencies.utils.jackson.serializer.LocalDateJsonSerializer;
import com.online4edu.dependencies.utils.jackson.serializer.LocalDateTimeJsonSerializer;
import com.online4edu.dependencies.utils.jackson.serializer.LocalTimeJsonSerializer;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
        mapperBuilder.serializerByType(LocalDate.class, new LocalDateJsonSerializer());
        mapperBuilder.serializerByType(LocalTime.class, new LocalTimeJsonSerializer());
        mapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        mapperBuilder.configure(objectMapper);


        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setPrettyPrint(true);
        jacksonConverter.setObjectMapper(objectMapper);
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));


        return new HttpMessageConverters(jacksonConverter);
    }


    /*@Bean
    public HttpMessageConverters xmlMessageConverters() {

        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.registerModule(createJavaTimeModel());

        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
        mapperBuilder.serializerByType(LocalDate.class, new LocalDateJsonSerializer());
        mapperBuilder.serializerByType(LocalTime.class, new LocalTimeJsonSerializer());
        mapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        mapperBuilder.configure(xmlMapper);

        MappingJackson2XmlHttpMessageConverter jacksonConverter = new MappingJackson2XmlHttpMessageConverter();
        jacksonConverter.setPrettyPrint(true);
        jacksonConverter.setObjectMapper(xmlMapper);
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_XML));

        return new HttpMessageConverters(jacksonConverter);
    }*/


    /*private static JavaTimeModule createJavaTimeModel() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // java8日期序列化
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // java8日期反序列化
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return javaTimeModule;
    }*/
}
