package com.teja.ratelimiter.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teja.ratelimiter.config.RateLimiterConfig;
import com.teja.ratelimiter.model.RateLimitResult;
import com.teja.ratelimiter.repository.RedisRepository;

@Component
public class SlidingWindowStrategy implements RateLimiterStrategy {

    @Autowired
    private RateLimiterConfig rateLimiterConfig;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public RateLimitResult allowRequest(String clientId) {

        String key = "rate_limit:" + clientId;

        long currentTime = System.currentTimeMillis();

        long windowStart =
                currentTime - (rateLimiterConfig.getWindowSeconds() * 1000L);

        redisRepository.removeOldTimestamps(key, windowStart);

        long requestCount = redisRepository.getRequestCount(key);

        // Request Limit Exceeded
        if (requestCount >= rateLimiterConfig.getMaxRequests()) {

            return new RateLimitResult(
                    false,
                    0,
                    rateLimiterConfig.getWindowSeconds(),
                    requestCount
            );
        }

        // Store current request
        redisRepository.addTimestamp(key, currentTime);

        redisRepository.setExpiry(
                key,
                rateLimiterConfig.getWindowSeconds());

        int remainingRequests =
                rateLimiterConfig.getMaxRequests() - (int) requestCount - 1;

        return new RateLimitResult(
                true,
                remainingRequests,
                0,
                requestCount + 1
        );
    }
}