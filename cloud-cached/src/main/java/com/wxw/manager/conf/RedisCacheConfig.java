package com.wxw.manager.conf;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.wxw.manager.function.Mybatis2RedisCached;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：wxw.
 * @date ： 14:52 2021/3/1
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@EnableCaching // 开启redis缓存
@EnableSpringHttpSession // 开启Spring Session 缓存
@Configuration
public class RedisCacheConfig {

    @Value("${server.servlet.session.timeout:720m}")
    private Duration sessionTimeout;

    /** 默认日期时间格式 */
    private static final String    DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    private static final String    DEFAULT_DATE_FORMAT      = "yyyy-MM-dd";
    /** 默认时间格式 */
    private static final String    DEFAULT_TIME_FORMAT      = "HH:mm:ss";
    /** redis数据库自定义key */
    public static final String     REDIS_KEY_DATABASE = "wxw";

    // redis 缓存
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        // 模板序列化
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    // mybatis 缓存
    @Bean
    public RedisTemplate<Object, Object> mybatisRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory)  {
        RedisTemplate<Object, Object> mybatisRedisTemplate = new RedisTemplate<>();
        mybatisRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        mybatisRedisTemplate.setKeySerializer(stringRedisSerializer);
        mybatisRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        mybatisRedisTemplate.setValueSerializer(fastJsonRedisSerializer);
        mybatisRedisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        mybatisRedisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        mybatisRedisTemplate.afterPropertiesSet();
        // mybatis 自定义二级缓存 到redis
        Mybatis2RedisCached.setRedisTemplate(mybatisRedisTemplate);
        return mybatisRedisTemplate;
    }

    // redis 序列化处理
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    // 缓存序列化管理
    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        //设置Redis缓存有效期为30天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    // session 缓存
    @Bean
    public SessionRepository sessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        RedisIndexedSessionRepository sessionRepository = new RedisIndexedSessionRepository(redisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval((int)sessionTimeout.getSeconds()); // 过期时间
        sessionRepository.setRedisKeyNamespace("spring:session:cached");
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        sessionRepository.setDefaultSerializer(fastJsonRedisSerializer);
        return sessionRepository;
    }


}
