# Distributed Rate Limiter using Java

This repository contains a distributed rate limiter built using **Spring Boot** and **Redis**. The project explores the implementation of scalable API rate limiting techniques that can be used in distributed backend applications.

The project currently implements the **Token Bucket** algorithm for per-client rate limiting, with **Sliding Window** support under active development. Redis is used as the centralized storage to maintain rate limiting state across application instances, and is run using **Docker** during development.

The application follows a clean layered architecture with separate modules for controllers, services, repositories, models, DTOs, and configuration.

## Architecture

```mermaid
flowchart LR

    Client[Client]

    LB[Load Balancer]

    subgraph Spring Boot Cluster
        A1[Application Instance 1]
        A2[Application Instance 2]
        AN[Application Instance N]
    end

    Redis[(Redis<br/>Shared Rate Limit Store)]

    Client -->|API Request| LB

    LB --> A1
    LB --> A2
    LB --> AN

    A1 <-->|Read / Update Token Bucket| Redis
    A2 <-->|Read / Update Token Bucket| Redis
    AN <-->|Read / Update Token Bucket| Redis
```

### How It Works

```mermaid
flowchart LR

    subgraph Application
        RC[RateLimiterController]
        RS[RateLimiterService]
        ST{RateLimiterStrategy}
    end

    subgraph Algorithms
        TB[Token Bucket]
        SW["Sliding Window (WIP)"]
    end

    Client[Client] --> RC
    RC --> RS
    RS --> ST

    ST --> TB
    ST -.-> SW

    TB <--> Redis[(Redis)]

    TB --> Decision{Within Limit?}

    Decision -->|Yes| Allow[HTTP 200 OK]
    Decision -->|No| Reject[HTTP 429 Too Many Requests]

    classDef client fill:#E3F2FD,stroke:#1E88E5,stroke-width:2px,color:#000;
    classDef app fill:#E8F5E9,stroke:#43A047,stroke-width:2px,color:#000;
    classDef algo fill:#FFF8E1,stroke:#FFB300,stroke-width:2px,color:#000;
    classDef redis fill:#FCE4EC,stroke:#D81B60,stroke-width:2px,color:#000;
    classDef decision fill:#F3E5F5,stroke:#8E24AA,stroke-width:2px,color:#000;
    classDef success fill:#E8F5E9,stroke:#2E7D32,stroke-width:2px,color:#000;
    classDef failure fill:#FFEBEE,stroke:#C62828,stroke-width:2px,color:#000;

    class Client client;
    class RC,RS,ST app;
    class TB,SW algo;
    class Redis redis;
    class Decision decision;
    class Allow success;
    class Reject failure;
```

### Current Features

- Token Bucket rate limiting
- Redis integration using Docker
- Per-client request limiting
- REST API for testing
- Health check endpoint
- Custom exception handling

### Work in Progress

- Sliding Window rate limiting algorithm
- Additional rate limiting strategies
- Unit & Integration tests
- Docker Compose setup
- Add monitoring and metrics

### Project Structure

- `controller/` - REST APIs
- `service/` - Rate limiting logic
- `repository/` - Redis operations
- `exception/` - Exception handling
- `model/` - Domain models
- `dto/` - Request & response objects
- `config/` - Application configuration

This project is being developed as a backend system design and distributed systems learning project, with a focus on building production-oriented rate limiting mechanisms.

Thank you! 🚀
