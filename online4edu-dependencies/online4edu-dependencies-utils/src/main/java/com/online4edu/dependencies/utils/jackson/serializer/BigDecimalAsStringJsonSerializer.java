package com.online4edu.dependencies.utils.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal 使用 Jackson 序列化时默认展示的是数字.
 * <p>
 * 如 <code>BigDecimal money = new BigDecimal("1.01");</code> 序列化得到的值是 1.01.
 * <p>
 * 该序列化类用于将其转化为字符串, 使其序列化后的值为 "1.01".
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/04/19 17:35
 */
public class BigDecimalAsStringJsonSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toPlainString());
    }
}
