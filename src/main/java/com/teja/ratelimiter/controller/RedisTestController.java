package com.teja.ratelimiter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teja.ratelimiter.repository.RedisRepository;

@RestController
public class RedisTestController {

    @Autowired
    private RedisRepository redisRepository;

    @GetMapping("/redis/test")
    public String testRedis() {

        redisRepository.save("test", "Hello Redis");

        return (String) redisRepository.get("test");
    }
}