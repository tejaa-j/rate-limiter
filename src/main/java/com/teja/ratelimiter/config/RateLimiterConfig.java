package com.teja.ratelimiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
	
//	Sliding Window
	
    @Value("${rate.limit.max-requests}")
    private int maxRequests;

    @Value("${rate.limit.window-seconds}")
    private int windowSeconds;

    public int getMaxRequests() {
        return maxRequests;
    }

    public int getWindowSeconds() {
        return windowSeconds;
    }

//    Token Bucket
    
    @Value("${rate.limit.bucket-capacity}")
    private int bucketCapacity;

    @Value("${rate.limit.refill-rate}")
    private int refillRate;
    
    public int getBucketCapacity() {
        return bucketCapacity;
    }

    public int getRefillRate() {
        return refillRate;
    }
}