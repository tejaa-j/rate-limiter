package com.teja.ratelimiter.service.strategy;

import org.springframework.stereotype.Component;

import com.teja.ratelimiter.config.RateLimiterConfig;
import com.teja.ratelimiter.model.RateLimitResult;
import com.teja.ratelimiter.model.TokenBucket;
import com.teja.ratelimiter.repository.RedisRepository;

@Component
public class TokenBucketStrategy implements RateLimiterStrategy {

    private final RedisRepository redisRepository;
    private final RateLimiterConfig config;

    public TokenBucketStrategy(RedisRepository redisRepository,
                               RateLimiterConfig config) {
        this.redisRepository = redisRepository;
        this.config = config;
    }

    @Override
    public RateLimitResult allowRequest(String clientId) {

        String key = "bucket:" + clientId;

        long now = System.currentTimeMillis();

        TokenBucket bucket =
                redisRepository.getObject(key, TokenBucket.class);

        if (bucket == null) {

            bucket = new TokenBucket(
                    config.getBucketCapacity(),
                    now
            );

        }

        refill(bucket, now);

        if (bucket.getTokens() < 1) {

            redisRepository.saveObject(key, bucket);

            double secondsNeeded =
                    (1 - bucket.getTokens())
                    / config.getRefillRate();

            long retryAfter =
                    (long) Math.ceil(secondsNeeded);

            return new RateLimitResult(
                    false,
                    0,
                    retryAfter,
                    Math.round(
                            config.getBucketCapacity()
                                    - bucket.getTokens())
            );

        }

        bucket.setTokens(bucket.getTokens() - 1);

        redisRepository.saveObject(key, bucket);

        return new RateLimitResult(
                true,
                (int) Math.floor(bucket.getTokens()),
                0,
                Math.round(
                        config.getBucketCapacity()
                                - bucket.getTokens())
        );

    }

    private void refill(TokenBucket bucket, long now) {

        long elapsedMillis = now - bucket.getLastRefillTime();

        if (elapsedMillis <= 0) {
            return;
        }

        double tokensToAdd =
                (elapsedMillis / 1000.0)
                * config.getRefillRate();

        bucket.setTokens(
                Math.min(
                        config.getBucketCapacity(),
                        bucket.getTokens() + tokensToAdd
                )
        );

        bucket.setLastRefillTime(now);

    }

}