package com.teja.ratelimiter.dto;

public class RateLimitResponse {

    private int status;
    private String message;
    private String clientId;
    private int remainingRequests;

    public RateLimitResponse() {
    }

    public RateLimitResponse(int status, String message, String clientId, int remainingRequests) {
        this.status = status;
        this.message = message;
        this.clientId = clientId;
        this.remainingRequests = remainingRequests;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getRemainingRequests() {
        return remainingRequests;
    }

    public void setRemainingRequests(int remainingRequests) {
        this.remainingRequests = remainingRequests;
    }

}