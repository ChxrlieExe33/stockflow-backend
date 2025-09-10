# StockFlow Backend

StockFlow is an inventory management and order system, designed for a furniture shop.

The backend is built using Java 24 with Spring Boot 3.5.5.

The key features of this application when it is fully done will be the following:

- _Security_
  - Full JWT authentication using Spring Security.
  - Role-based access control for sensitive endpoints. (RBAC)
  - Internal account creation, done only by users with the administrator role.
  - Reset password functionality.
  - CORS configuration.
  - CSRF protection.
  - Submitted text validation to avoid XSS.
- _Data and performance_
  - DTO projections for complex stock counting queries to avoid over-fetching data.
  - Asynchronous event listeners, used for handling side effects without blocking the request thread.
- _General_
  - Usage of the DTO pattern to avoid returning sensitive data to the users.
  - Global exception handling to return clean error messages to the client when a controlled-failure occurs.
  - Unit tests using JUnit5 and Mockito, alongside AssertJ for more readable assertions.