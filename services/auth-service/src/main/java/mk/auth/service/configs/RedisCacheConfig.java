package mk.auth.service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        // Lấy ClassLoader hiện tại, cần thiết cho JdkSerializationRedisSerializer
        ClassLoader classLoader = this.getClass().getClassLoader();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(classLoader);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .disableCachingNullValues()
                // Sử dụng Java serialization
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer));
    }
}