package com.arthur.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 缓存配置
 * @author Arthur
 */
@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * 创建一个CacheManager对象，用于管理缓存
     */
    @Bean
    public CacheManager cacheManager (RedisConnectionFactory factory, RedisSerializer<Object> serializer) {
        // 创建一个RedisCacheManager对象，用于管理Redis缓存
        RedisCacheManager manager = new RedisCacheManager(
                // 创建一个RedisCacheWriter对象，用于写入Redis缓存
                RedisCacheWriter.nonLockingRedisCacheWriter(factory),
                // 获取Redis缓存配置，设置缓存过期时间为3600秒
                this.getRedisCacheConfigurationWithTtl(3600, serializer),
                // 获取Redis缓存配置的Map
                this.getRedisCacheConfigurationMap(serializer)
        );
        // 设置事务感知为true
        manager.setTransactionAware(true);
        // 返回CacheManager对象
        return manager;
    }

    /**
     * 定义一个RedisTemplate的Bean
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate (RedisConnectionFactory factory, RedisSerializer<Object> serializer) {
        // 创建一个RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置Redis连接工厂
        template.setConnectionFactory(factory);
        // 设置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // 设置hash key的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // 设置value的序列化方式
        template.setValueSerializer(serializer);
        // 设置hash value的序列化方式
        template.setHashValueSerializer(serializer);
        // 禁用事务支持
        template.setEnableTransactionSupport(false);

       // 初始化RedisTemplate
       template.afterPropertiesSet();
       // 返回RedisTemplate对象
       return template;
    }

    /*
     * 定义redis序列化的机制,重新定义一个ObjectMapper.防止和MVC的冲突
     */
    @Bean
    public RedisSerializer<Object> redisSerializer () {
        // 创建ObjectMapper对象
        ObjectMapper mapper = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();
        // 反序列化时候遇到不匹配的属性并不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时候遇到空对象不抛出异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化的时候如果是无效子类型,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        // 不使用默认的dateTime进行序列化,
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        mapper.registerModule(new JavaTimeModule());
        // 启用反序列化所需的类型信息,在属性中添加@class
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        // 配置null值的序列化器
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(mapper, null);
        // 返回GenericJackson2JsonRedisSerializer对象
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    /**
     * 创建一个StringRedisTemplate的Bean
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 使用RedisConnectionFactory创建一个StringRedisTemplate对象
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        // 设置是否启用事务支持，这里设置为false
        redisTemplate.setEnableTransactionSupport(false);
        // 返回StringRedisTemplate对象
        return redisTemplate;
    }

    /**
     * 根据传入的序列化器，获取Redis缓存配置的Map
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap (RedisSerializer<Object> serializer) {
        // 创建一个容量为16的HashMap
        Map<String, RedisCacheConfiguration> map = new HashMap<>(16);
        // 将product缓存配置放入Map中，缓存时间为1800秒
        map.put("product", this.getRedisCacheConfigurationWithTtl(1800, serializer));
        // 返回Map
        return map;
    }

    /**
     * 根据指定的过期时间和序列化器，获取Redis缓存配置
     */
    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl (long seconds, RedisSerializer<Object> serializer) {
        // 创建默认的Redis缓存配置
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存值的序列化器
        return configuration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(serializer)
        ).entryTtl(Duration.ofSeconds(seconds));
    }

}
