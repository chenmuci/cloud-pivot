package com.arthur.common.serializable;

import com.arthur.common.annotation.Desensitization;
import com.arthur.common.enums.DesensitizationEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * 自定义的脱敏JSON序列化器
 * @author Arthur
 */
public class DesensitizationJsonSerializable extends JsonSerializer<String> implements ContextualSerializer {

    private DesensitizationEnum desensitization;

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(desensitization.getDesensitization().apply(s));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 获取属性上的 Desensitization 注解
        Desensitization annotation = beanProperty.getAnnotation(Desensitization.class);

        // 判断注解不为空且属性类型为 String
        if (Objects.nonNull(annotation) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            // 设置脱敏策略
            this.desensitization = annotation.function();
            return this;
        }

        // 返回默认的序列化器
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
