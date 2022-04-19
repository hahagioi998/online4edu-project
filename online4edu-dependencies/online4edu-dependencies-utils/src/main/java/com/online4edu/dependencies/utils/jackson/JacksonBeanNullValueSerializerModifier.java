package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Jackson 序列化时对 NULL 值处理
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/04/19 16:21
 */
public class JacksonBeanNullValueSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        for (BeanPropertyWriter writer : beanProperties) {

            JavaType javaType = writer.getType();
            Class<?> rawClass = javaType.getRawClass();

            if (javaType.isMapLikeType()) {
                // Map Fill {}
                writer.assignNullSerializer(new NullMapSerializer());

            } else if (javaType.isArrayType() || javaType.isCollectionLikeType()) {
                // Collection Fill []
                writer.assignNullSerializer(new NullCollectionSerializer());

            } else if (rawClass.isAssignableFrom(Boolean.class)) {
                // Boolean Fill false
                writer.assignNullSerializer(new NullBooleanSerializer());

            } else if (rawClass.isAssignableFrom(BigDecimal.class)) {
                // Boolean Fill ZERO
                writer.assignNullSerializer(new NullBigDecimalSerializer());

            }  else if (rawClass.isAssignableFrom(Number.class)) {
                // Boolean Fill ZERO
                writer.assignNullSerializer(new NullNumberSerializer());

            } else if (rawClass.isAssignableFrom(String.class)) {
                // Boolean Fill ""
                writer.assignNullSerializer(new NullStringSerializer());

            }
        }
        return beanProperties;
    }


    // ===============================
    // =============================== Null Value Processor 
    // =============================== 


    public static class NullMapSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // {}
            gen.writeStartObject();
            gen.writeEndObject();
        }
    }

    public static class NullCollectionSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // []
            gen.writeStartArray();
            gen.writeEndArray();
        }
    }

    public static class NullBooleanSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeBoolean(false);
        }
    }

    public static class NullBigDecimalSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString("0");
        }
    }
    
    public static class NullNumberSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(0);
        }
    }

    public static class NullStringSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString("");
        }
    }
}
