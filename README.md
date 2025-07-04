# 📦 Order-Service

A resilient Spring Boot microservice that manages customer orders and communicates with external services (e.g., Product-Service) via **OpenFeign**. It includes circuit breaker support using **Resilience4j** to ensure service reliability.

---

## ✨ Features

- Create and fetch orders
- Calls Product-Service using **Feign Client**
- Circuit breaker, fallback, and timeouts with **Resilience4j**
- Global exception handling
- DTO-based architecture for clean request/response modeling

---

## 🧰 Tech Stack

- Java 17
- Spring Boot
- Spring Cloud OpenFeign
- Resilience4j
- Spring Web
- Spring Data JPA
- PostGreSQL
- Gradle

---

## 🏗️ Project Structure

src
├── controller # REST APIs for order handling
├── dto # Order and Product DTOs
├── entity # Order JPA entity
├── exception # Custom/global error handling
├── external # Feign client to Product-Service
├── repository # JPA Repository for Order
├── service # Business logic
└── OrderServiceApplication.java


---

## 🌐 Inter-Service Communication

Uses **OpenFeign** to call Product-Service like so:

```java
@FeignClient(name = "product-service", url = "http://localhost:8081", fallback = ProductFallback.class)
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable Long id);
}
Includes fallback logic using ProductFallback.

🛡️ Resilience with Resilience4j

Resilience4j is used to prevent cascading failures by:

Circuit Breaker
Time Limiter
Fallback mechanism
Example annotation:

@CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
public ProductDto getProductDetails(Long productId) {
    return productClient.getProductById(productId);
}
🚀 Getting Started

1. Clone the Repository
git clone https://github.com/iamsujatasingh/Order-Service.git
cd Order-Service
2. Build & Run
./gradlew build
./gradlew bootRun
The app will start at:
http://localhost:8080

ℹ️ Make sure Product-Service is running on http://localhost:8081 if calling real endpoints.
🔁 API Endpoints

Method	Endpoint	Description
POST	/orders	Create a new order
GET	/orders	Fetch all orders
📥 Sample POST Request
{
  "orderName": "Test Order",
  "productId": 1,
  "quantity": 3,
  "price": 99.99
}
🧪 Testing

./gradlew test
✅ Add unit and integration tests using JUnit and Mockito to cover fallback and Feign logic.
🛠️ Configuration Notes

application.yml
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000

resilience4j:
  circuitbreaker:
    instances:
      productService:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
📦 Roadmap

 Feign client integration
 Resilience4j fallback
 Add OpenAPI/Swagger docs
 Unit/integration test coverage
 Dockerize with docker-compose
 Secure with JWT (Spring Security)
👤 Author

Sujata Singh
🔗 GitHub
