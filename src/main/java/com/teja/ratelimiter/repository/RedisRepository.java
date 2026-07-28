package com.teja.ratelimiter.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
    
//    Sliding Window Methods

    public void addTimestamp(String key, long timestamp) {
        redisTemplate.opsForZSet().add(key, timestamp, timestamp);
    }

    public void removeOldTimestamps(String key, long windowStart) {
        redisTemplate.opsForZSet()
                .removeRangeByScore(key, 0, windowStart);
    }

    public long getRequestCount(String key) {
        Long count = redisTemplate.opsForZSet().zCard(key);
        return count == null ? 0 : count;
    }

    public void setExpiry(String key, long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public Set<Object> getAllRequests(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
    
//    Generic Methods
    
    public void saveObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getObject(String key, Class<T> clazz) {

        Object value = redisTemplate.opsForValue().get(key);

        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }

        return null;
    }
}