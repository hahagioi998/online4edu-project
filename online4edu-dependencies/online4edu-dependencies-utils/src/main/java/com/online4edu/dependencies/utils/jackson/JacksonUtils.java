package com.online4edu.dependencies.utils.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.online4edu.dependencies.utils.exception.DeserializationException;
import com.online4edu.dependencies.utils.exception.SerializationException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * Json utils implement by Jackson.
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
public final class JacksonUtils {

    static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(Include.NON_NULL);
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
            return mapper.writeValueAsString(obj);
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
            String json = mapper.writeValueAsString(obj);
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
            return mapper.readValue(inputStream, clazz);
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
            return mapper.readValue(json, clazz);
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
            return mapper.readValue(json, mapper.constructType(type));
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
            return mapper.readValue(json, typeReference);
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
            return mapper.readValue(inputStream, mapper.constructType(type));
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
            return mapper.readTree(json);
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
        mapper.registerSubtypes(new NamedType(clazz, type));
    }

    /**
     * Create a new empty Jackson {@link ObjectNode}.
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(mapper.getNodeFactory());
    }

    /**
     * Create a new empty Jackson {@link ArrayNode}.
     *
     * @return {@link ArrayNode}
     */
    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(mapper.getNodeFactory());
    }

    /**
     * Parse object to Jackson {@link JsonNode}.
     *
     * @param obj object
     * @return {@link JsonNode}
     */
    public static JsonNode transferToJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    /**
     * construct java type -> Jackson Java Type.
     *
     * @param type java type
     * @return JavaType {@link JavaType}
     */
    public static JavaType constructJavaType(Type type) {
        return mapper.constructType(type);
    }
}
