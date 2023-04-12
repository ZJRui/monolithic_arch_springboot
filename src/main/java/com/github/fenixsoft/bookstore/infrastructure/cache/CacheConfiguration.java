package com.github.fenixsoft.bookstore.infrastructure.cache;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    /**
     * 系统默认缓存TTL
     *
     */
    public static final long SYSTEM_DEFAULT_TTL = 3600;

    @Bean
    public CacheManager     cacheManager() {
        CaffeineCacheManager caffeineCacheManager=new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(SYSTEM_DEFAULT_TTL, TimeUnit.SECONDS));
        return caffeineCacheManager;
    }

    @Bean(name="settlement")
    public Cache getSettlementTTLCache(){

        return new CaffeineCache("settlement", Caffeine.newBuilder().expireAfterWrite(SYSTEM_DEFAULT_TTL, TimeUnit.SECONDS).build());

    }
}
