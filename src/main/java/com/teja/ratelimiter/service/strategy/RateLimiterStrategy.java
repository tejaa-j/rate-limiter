package com.teja.ratelimiter.service.strategy;

import com.teja.ratelimiter.model.RateLimitResult;

public interface RateLimiterStrategy {

    RateLimitResult allowRequest(String clientId);

}