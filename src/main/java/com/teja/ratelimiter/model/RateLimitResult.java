package com.teja.ratelimiter.model;

public class RateLimitResult {

    private boolean allowed;
    private int remainingRequests;
    private long retryAfter;
    private long currentRequestCount;

    public RateLimitResult() {
    }

    public RateLimitResult(boolean allowed, int remainingRequests,
                           long retryAfter, long currentRequestCount) {
        this.allowed = allowed;
        this.remainingRequests = remainingRequests;
        this.retryAfter = retryAfter;
        this.currentRequestCount = currentRequestCount;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public int getRemainingRequests() {
        return remainingRequests;
    }

    public void setRemainingRequests(int remainingRequests) {
        this.remainingRequests = remainingRequests;
    }

    public long getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(long retryAfter) {
        this.retryAfter = retryAfter;
    }

    public long getCurrentRequestCount() {
        return currentRequestCount;
    }

    public void setCurrentRequestCount(long currentRequestCount) {
        this.currentRequestCount = currentRequestCount;
    }
}