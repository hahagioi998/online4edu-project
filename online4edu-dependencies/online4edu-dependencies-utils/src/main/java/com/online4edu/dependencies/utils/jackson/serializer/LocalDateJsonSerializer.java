package com.online4edu.dependencies.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.online4edu.dependencies.utils.datetime.DateTimePattern;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Jackson 自定义日期反序列化扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 16:38
 */
public class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {
    
    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(date.format(DateTimeFormatter.ofPattern(DateTimePattern.DATE_PATTERN, Locale.CHINA)));
    }
}