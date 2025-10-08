# Microservices Architecture - Java Tutorial

A comprehensive microservices-based e-commerce application built with Spring Boot and Spring Cloud, demonstrating modern cloud-native architecture patterns and best practices.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Services](#services)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Service Ports](#service-ports)
- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## 🎯 Overview

This project demonstrates a production-ready microservices architecture implementing an e-commerce platform. It showcases service discovery, centralized configuration, API gateway, distributed tracing, event-driven communication, and more.

## 🏗️ Architecture

The application follows a microservices architecture pattern with the following components:

```
┌─────────────┐
│   Clients   │
└──────┬──────┘
       │
┌──────▼──────────────┐
│  Gateway Service    │  (API Gateway - Routes requests)
└──────┬──────────────┘
       │
┌──────▼──────────────┐
│ Discovery Service   │  (Eureka - Service Registry)
└──────┬──────────────┘
       │
┌──────┴──────────────────────────────────────────┐
│                                                  │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────┐ │
│  │   Order     │  │  Customer   │  │ Product │ │
│  │  Service    │  │   Service   │  │ Service │ │
│  └─────────────┘  └─────────────┘  └─────────┘ │
│                                                  │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────┐ │
│  │  Payment    │  │    Auth     │  │  User   │ │
│  │  Service    │  │   Service   │  │ Service │ │
│  └─────────────┘  └─────────────┘  └─────────┘ │
│                                                  │
│  ┌──────────────────────────────┐               │
│  │   Notification Service       │               │
│  │   (Event-Driven with Kafka)  │               │
│  └──────────────────────────────┘               │
└──────────────────────────────────────────────────┘
```

## 🚀 Services

### Infrastructure Services

#### 1. **Config Server** (`config-server`)
- Centralized configuration management for all microservices
- Manages environment-specific configurations
- Port: `8888`

#### 2. **Discovery Service** (`discovery-service`)
- Service registry using Netflix Eureka
- Enables service discovery and load balancing
- Dynamic service registration and health monitoring
- Port: `8761`

#### 3. **Gateway Service** (`gateway-service`)
- API Gateway using Spring Cloud Gateway
- Single entry point for all client requests
- Request routing, load balancing, and security
- Cross-cutting concerns (logging, monitoring)

### Business Services

#### 4. **Auth Service** (`auth-service`)
- Authentication and authorization
- JWT token generation and validation
- User credential management

#### 5. **Customer Service** (`customer-service`)
- Customer management and profile operations
- Uses MongoDB for data persistence
- Integration with other services via OpenFeign

#### 6. **Product Service** (`product-service`)
- Product catalog management
- Inventory tracking
- Product information and pricing

#### 7. **Order Service** (`order-service`)
- Order processing and management
- Order lifecycle handling
- Integration with payment and inventory services

#### 8. **Payment Service** (`payment-service`)
- Payment processing
- Transaction management
- Payment gateway integration

#### 9. **Notification Service** (`notification-service`)
- Event-driven notification system
- Email notifications using Spring Mail
- Kafka consumer for asynchronous event processing
- Uses MongoDB and Thymeleaf for email templates

#### 10. **User Service** (`user-service`)
- User account management
- User profile operations

## 🛠️ Technology Stack

### Core Framework
- **Spring Boot 3.4.4** - Application framework
- **Java 17** - Programming language
- **Maven** - Dependency management and build tool

### Spring Cloud Components
- **Spring Cloud 2024.0.1**
- **Spring Cloud Config** - Centralized configuration
- **Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway
- **OpenFeign** - Declarative REST clients

### Databases
- **PostgreSQL** - Relational database
- **MongoDB** - NoSQL database for customer and notification services

### Message Broker
- **Apache Kafka** - Event streaming platform
- **Zookeeper** - Kafka coordination

### Monitoring & Tracing
- **Zipkin** - Distributed tracing system

### Email
- **Spring Mail** - Email sending
- **MailDev** - Email testing tool (development)
- **Thymeleaf** - Email template engine

### Additional Technologies
- **Docker & Docker Compose** - Containerization
- **Lombok** - Boilerplate code reduction
- **Bean Validation** - Input validation

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **Git**

## 🚦 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Microservices
```

### 2. Start Infrastructure Services

Start all required infrastructure services using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL (Port: 5432)
- MongoDB (Port: 27017)
- Kafka (Port: 9092)
- Zookeeper (Port: 2181)
- Zipkin (Port: 9411)
- MailDev (Ports: 1080 for UI, 1025 for SMTP)

### 3. Start Microservices

Start the services in the following order:

#### Step 1: Config Server
```bash
cd services/config-server
mvnw spring-boot:run
```

#### Step 2: Discovery Service
```bash
cd services/discovery-service
mvnw spring-boot:run
```

#### Step 3: Gateway Service
```bash
cd services/gateway-service
mvnw spring-boot:run
```

#### Step 4: Business Services
Start all business services (can be run in parallel):

```bash
# Auth Service
cd services/auth-service
mvnw spring-boot:run

# Customer Service
cd services/customer-service
mvnw spring-boot:run

# Product Service
cd services/product-service
mvnw spring-boot:run

# Order Service
cd services/order-service
mvnw spring-boot:run

# Payment Service
cd services/payment-service
mvnw spring-boot:run

# Notification Service
cd services/notification-service
mvnw spring-boot:run

# User Service
cd services/user-service
mvnw spring-boot:run
```

## 🔌 Service Ports

| Service | Port | Description |
|---------|------|-------------|
| Config Server | 8888 | Configuration management |
| Discovery Service | 8761 | Eureka dashboard |
| Gateway Service | 8080 | API Gateway endpoint |
| PostgreSQL | 5432 | Relational database |
| MongoDB | 27017 | NoSQL database |
| Kafka | 9092 | Message broker |
| Zookeeper | 2181 | Kafka coordination |
| Zipkin | 9411 | Distributed tracing UI |
| MailDev UI | 1080 | Email testing interface |
| MailDev SMTP | 1025 | SMTP server |

## ✨ Key Features

### Service Discovery & Registration
- Automatic service registration with Eureka
- Dynamic service discovery
- Health check monitoring
- Load balancing

### Centralized Configuration
- Environment-specific configuration management
- Configuration refresh without restart
- Externalized configuration from Spring Cloud Config Server

### API Gateway
- Single entry point for all services
- Request routing and filtering
- Load balancing
- Security and rate limiting

### Inter-Service Communication
- Synchronous communication using OpenFeign
- Asynchronous communication using Kafka

### Event-Driven Architecture
- Kafka-based event streaming
- Asynchronous notification processing
- Decoupled services communication

### Distributed Tracing
- Request tracing across services using Zipkin
- Performance monitoring
- Troubleshooting distributed transactions

### Database Per Service
- PostgreSQL for transactional data
- MongoDB for flexible schema requirements
- Polyglot persistence pattern

### Email Notifications
- Template-based email generation
- Asynchronous email sending
- Development email testing with MailDev

## 📁 Project Structure

```
Microservices/
├── docker-compose.yml              # Infrastructure services
├── diagrams/                       # Architecture diagrams
│   └── micro-services.drawio
└── services/                       # All microservices
    ├── config-server/
    ├── discovery-service/
    ├── gateway-service/
    ├── auth-service/
    ├── customer-service/
    ├── product-service/
    ├── order-service/
    ├── payment-service/
    ├── notification-service/
    ├── user-service/
    └── lamdaHandler/               # AWS Lambda function (optional)
```

Each service follows the standard Maven project structure:
```
service-name/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
└── mvnw / mvnw.cmd
```

## 🔍 Accessing the Services

### Eureka Dashboard
Monitor all registered services:
```
http://localhost:8761
```

### Zipkin UI
View distributed traces:
```
http://localhost:9411
```

### MailDev UI
View sent emails (development):
```
http://localhost:1080
```

### API Gateway
Access all services through the gateway:
```
http://localhost:8080/{service-name}/{endpoint}
```

## 🧪 Testing

Each service includes unit tests. To run tests for a specific service:

```bash
cd services/{service-name}
mvnw test
```

To run tests for all services:

```bash
# Run from root directory
for /d %d in (services\*) do (cd %d && mvnw test && cd ..\..)
```

## 📊 Monitoring & Health Checks

All services expose actuator endpoints for health monitoring:

```
http://localhost:{port}/actuator/health
```

## 🛡️ Security

- JWT-based authentication via Auth Service
- API Gateway handles security concerns
- Service-to-service authentication
- Secure communication between services

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is for educational purposes as part of the Java Tutorial series.

## 📧 Contact

For questions or suggestions, please open an issue in the repository.

---

**Happy Coding! 🚀**

