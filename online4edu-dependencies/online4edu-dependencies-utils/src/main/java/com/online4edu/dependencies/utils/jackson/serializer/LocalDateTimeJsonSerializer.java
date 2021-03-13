package com.online4edu.dependencies.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.online4edu.dependencies.utils.datetime.DateTimePattern;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Jackson 自定义日期时间反序列化扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 16:38
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(dateTime.format(DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME_PATTERN, Locale.CHINA)));
    }
}