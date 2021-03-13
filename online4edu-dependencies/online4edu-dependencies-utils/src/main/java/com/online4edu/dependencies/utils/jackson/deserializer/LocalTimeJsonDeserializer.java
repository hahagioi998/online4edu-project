package com.online4edu.dependencies.utils.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.online4edu.dependencies.utils.datetime.DateTimePattern;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson 自定义时间序列化扩展
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 16:38
 */
public class LocalTimeJsonDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalTime.parse(parser.getText(), DateTimeFormatter.ofPattern(DateTimePattern.TIME_PATTERN));
    }
}