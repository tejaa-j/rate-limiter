package com.teja.ratelimiter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teja.ratelimiter.dto.RateLimitResponse;
import com.teja.ratelimiter.exception.RateLimitExceededException;
import com.teja.ratelimiter.model.RateLimitResult;
import com.teja.ratelimiter.service.RateLimiterService;

@RestController
public class RateLimiterController {

    private RateLimiterService rateLimiterService;
    
    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/api/request")
    public ResponseEntity<RateLimitResponse> processRequest(
            @RequestParam String clientId) {

    	RateLimitResult result = rateLimiterService.allowRequest(clientId);

    	if (!result.isAllowed()) {
    	    throw new RateLimitExceededException(
    	            "Rate Limit Exceeded. Retry after "
    	                    + result.getRetryAfter()
    	                    + " seconds.");
    	}

    	RateLimitResponse response =
    	        new RateLimitResponse(
    	                200,
    	                "Request Allowed",
    	                clientId,
    	                result.getRemainingRequests()
    	        );

        return ResponseEntity.ok(response);
    }
}