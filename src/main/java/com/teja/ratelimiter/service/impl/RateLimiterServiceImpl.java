package com.teja.ratelimiter.service.impl;

import org.springframework.stereotype.Service;

import com.teja.ratelimiter.model.RateLimitResult;
import com.teja.ratelimiter.service.RateLimiterService;
import com.teja.ratelimiter.service.strategy.RateLimiterStrategyFactory;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

	private final RateLimiterStrategyFactory strategyFactory;

	public RateLimiterServiceImpl(RateLimiterStrategyFactory strategyFactory) {
	    this.strategyFactory = strategyFactory;
	}

    @Override
    public RateLimitResult allowRequest(String clientId) {
    	return strategyFactory.getStrategy().allowRequest(clientId);
    }
}