package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.online4edu.dependencies.utils.datetime.DateTimePattern;
import com.online4edu.dependencies.utils.exception.DeserializationException;
import com.online4edu.dependencies.utils.exception.SerializationException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Json utils implement by Jackson.
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
public final class JacksonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final XmlMapper XML = new XmlMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 序列化所有字段
        MAPPER.setSerializationInclusion(Include.ALWAYS);
        // 设置时区
        MAPPER.setTimeZone(TimeZone.getDefault());
        // 忽略 transient 字段
        MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        // java.util.Date 日期格式 处理
        MAPPER.setDateFormat(new SimpleDateFormat(DateTimePattern.DATE_TIME_PATTERN));
        // java.time.* 日期格式处理
        configureObjectMapper4Jsr310(MAPPER);
    }

    /**
     * Get ObjectMapper Instance
     */
    public static ObjectMapper createObjectMapper() {
        return MAPPER;
    }

    /**
     * Get XmlMapper Instance
     */
    public static XmlMapper createXmlMapper() {
        return XML;
    }

    /**
     * Object to json string.
     *
     * @param obj obj
     * @return json string
     * @throws SerializationException if transfer failed
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws SerializationException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            String json = MAPPER.writeValueAsString(obj);
            if (StringUtils.isNotBlank(json)) {
                return json.getBytes(StandardCharsets.UTF_8);
            }
            return new byte[0];
        } catch (JsonProcessingException e) {
            throw new SerializationException(obj.getClass(), e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz class of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Class<T> clazz) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {

            throw new DeserializationException(clazz, e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz {@link Type} of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Type clazz) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), clazz);
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param clazz       class of object
     * @param <T>         General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Class<T> clazz) {
        try {
            return MAPPER.readValue(inputStream, clazz);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string byte array
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), typeReference);
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json  json string
     * @param clazz class of object
     * @param <T>   General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new DeserializationException(clazz, e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param type {@link Type} of object
     * @param <T>  General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, Type type) {
        try {
            return MAPPER.readValue(json, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new DeserializationException(typeReference.getClass(), e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param type        {@link Type} of object
     * @param <T>         General type
     * @return object
     * @throws DeserializationException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Type type) {
        try {
            return MAPPER.readValue(inputStream, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new DeserializationException(type, e);
        }
    }

    /**
     * Json string deserialize to Jackson {@link JsonNode}.
     *
     * @param json json string
     * @return {@link JsonNode}
     * @throws DeserializationException if deserialize failed
     */
    public static JsonNode toObj(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    /**
     * Register sub type for child class.
     *
     * @param clazz child class
     * @param type  type name of child class
     */
    public static void registerSubtype(Class<?> clazz, String type) {
        MAPPER.registerSubtypes(new NamedType(clazz, type));
    }

    /**
     * Create a new empty Jackson {@link ObjectNode}.
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(MAPPER.getNodeFactory());
    }

    /**
     * Create a new empty Jackson {@link ArrayNode}.
     *
     * @return {@link ArrayNode}
     */
    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(MAPPER.getNodeFactory());
    }

    /**
     * Parse object to Jackson {@link JsonNode}.
     *
     * @param obj object
     * @return {@link JsonNode}
     */
    public static JsonNode transferToJsonNode(Object obj) {
        return MAPPER.valueToTree(obj);
    }

    /**
     * construct java type -> Jackson Java Type.
     *
     * @param type java type
     * @return JavaType {@link JavaType}
     */
    public static JavaType constructJavaType(Type type) {
        return MAPPER.constructType(type);
    }

    /**
     * 配置 Java8 日期处理格式
     */
    private static void configureObjectMapper4Jsr310(ObjectMapper objectMapper) {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // LocalTime 序列化和反序列化配置
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateTimePattern.TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        // LocalDate 序列化和反序列化配置
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DateTimePattern.DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        // LocalDateTime 序列化和反序列化配置
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(datetimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(datetimeFormatter));

        objectMapper.registerModule(javaTimeModule);

    }
}
