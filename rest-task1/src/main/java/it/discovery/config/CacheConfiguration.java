package it.discovery.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.KryoCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfiguration {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient client() {
        Config config = new Config();
        config.setNettyThreads(2);
        config.setCodec(new KryoCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        return Redisson.create(config);
    }

    @Bean
    public RedissonSpringCacheManager cacheManager(RedissonClient client) {
        Map<String, CacheConfig> cacheConfig = new HashMap<>();
        cacheConfig.put("books", new CacheConfig(30000, 300000));
        return new RedissonSpringCacheManager(client, cacheConfig);
    }
}
