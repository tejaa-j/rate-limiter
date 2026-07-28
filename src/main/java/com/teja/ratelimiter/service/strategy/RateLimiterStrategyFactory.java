package com.teja.ratelimiter.service.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterStrategyFactory {

    private final SlidingWindowStrategy slidingWindowStrategy;
    private final TokenBucketStrategy tokenBucketStrategy;

    @Value("${rate.limit.algorithm}")
    private String algorithm;

    public RateLimiterStrategyFactory(
            SlidingWindowStrategy slidingWindowStrategy,
            TokenBucketStrategy tokenBucketStrategy) {

        this.slidingWindowStrategy = slidingWindowStrategy;
        this.tokenBucketStrategy = tokenBucketStrategy;
    }

    public RateLimiterStrategy getStrategy() {

        if ("token-bucket".equalsIgnoreCase(algorithm)) {
            return tokenBucketStrategy;
        }

        return slidingWindowStrategy;
    }

}