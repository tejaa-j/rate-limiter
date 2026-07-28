package com.teja.ratelimiter.service;

import com.teja.ratelimiter.model.RateLimitResult;

public interface RateLimiterService {

    RateLimitResult allowRequest(String clientId);

}