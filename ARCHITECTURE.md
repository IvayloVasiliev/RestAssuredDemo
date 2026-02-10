# Project Architecture & Design Patterns

## Overview

This API Automation Testing Framework follows enterprise-level design patterns and architectural principles to ensure maintainability, scalability, and code quality.

---

## 🏛️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Test Classes                              │
│   ┌──────────────────┬──────────────────┐                   │
│   │ BooksHappyPath   │ BooksEdgeCase    │ AuthorsHappyPath │
│   └────────┬─────────┴────────┬─────────┴─────────┬─────────┘
│            │                  │                   │
│            └──────────────────┼───────────────────┘
│                               │
│                    Extends: BaseTest
│                               │
│    ┌──────────────────────────┼──────────────────────────┐
│    │                          │                          │
│    ▼                          ▼                          ▼
│ ┌─────────────┐        ┌──────────────┐        ┌─────────────┐
│ │ ApiRequest  │        │ Assertion    │        │ ApiConfig   │
│ │ Helper      │        │ Helper       │        │             │
│ └─────────────┘        └──────────────┘        └─────────────┘
│    │                          │                          │
│    └──────────────────────────┼──────────────────────────┘
│                               │
│                ┌──────────────┴──────────────┐
│                │                             │
│                ▼                             ▼
│        ┌──────────────────┐        ┌─────────────────┐
│        │   Models         │        │ Data Validation │
│        │ ┌────────────┐   │        │                 │
│        │ │ Book       │   │        │ - Book.isValid()│
│        │ │ Author     │   │        │ - Author.is...()│
│        │ └────────────┘   │        │                 │
│        └──────────────────┘        └─────────────────┘
│                │                             │
│                └─────────────┬───────────────┘
│                              │
│                    RestAssured Library
│                              │
└──────────────────────────────┼──────────────────────────────┘
                               │
                    ┌──────────┴──────────┐
                    │                     │
                    ▼                     ▼
            FakeRestAPI Server      HTTP/HTTPS Layer
```

---

## 📦 Package Structure

### 1. **base/** - Base Classes
- **BaseTest.java** - Common test setup, teardown, logging
  - Lifecycle management (BeforeClass, AfterClass)
  - Structured logging methods
  - Step-by-step test execution logging

### 2. **config/** - Configuration
- **ApiConfig.java** - Centralized configuration
  - Base URL and endpoints
  - HTTP status codes
  - Timeouts and headers
  - Convenience methods for building URLs

### 3. **models/** - Data Models
- **Book.java** - Book entity model
  - Fields: id, title, description, pageCount, excerpt, publishDate
  - Validation methods: isValid(), hasMinimalFields()
  - Serialization/deserialization for JSON

- **Author.java** - Author entity model
  - Fields: id, firstName, lastName, idBook
  - Validation methods
  - Helper methods: getFullName()

### 4. **utils/** - Helper Classes
- **ApiRequestHelper.java** - API request abstraction
  - Generic request methods (GET, POST, PUT, DELETE, PATCH)
  - Request specification building
  - Response logging

- **AssertionHelper.java** - Custom assertions
  - Status code assertions
  - Response body assertions
  - Header assertions
  - Performance assertions

### 5. **tests/** - Test Cases
- **tests/books/** - Books API tests
  - BooksHappyPathTest.java - Positive scenarios
  - BooksEdgeCaseTest.java - Edge cases and errors

- **tests/authors/** - Authors API tests
  - AuthorsHappyPathTest.java - Positive scenarios

---

## 🎯 Design Patterns Applied

### 1. **Singleton Pattern**
```java
// ApiConfig acts as a singleton-like configuration holder
public static final String BASE_URL = "https://fakerestapi.azurewebsites.net";
public static String getEndpointURL(String endpoint) { ... }
```

### 2. **Builder Pattern**
```java
// Models use Builder for object construction
Book book = Book.builder()
    .title("Test Book")
    .pageCount(300)
    .build();
```

### 3. **Template Method Pattern**
```java
// BaseTest provides template for test execution
public void setUp() { ... }
protected void logSection(String name) { ... }
public void tearDown() { ... }
```

### 4. **Helper Pattern**
```java
// ApiRequestHelper encapsulates request logic
public static Response getRequest(String url) { ... }
public static Response postRequest(String url, Object body) { ... }
```

### 5. **Data Transfer Object (DTO)**
```java
// Models act as DTOs for API communication
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book { ... }
```

---

## 🔄 Data Flow

### Test Execution Flow

```
1. Test Class Instantiation
        ↓
2. @BeforeClass - setUp()
        ↓
3. @Test Method - Test Logic
   ├─ logSection()
   ├─ logStep()
   ├─ ApiRequestHelper.request()
   │  ├─ RestAssured.given()
   │  ├─ Add headers
   │  └─ Execute HTTP call
   ├─ AssertionHelper.assert*()
   │  └─ Validate response
   └─ logSuccess/logFailure()
        ↓
4. @AfterClass - tearDown()
        ↓
5. Test Report Generation
        ↓
6. Artifacts Archival
```

### Request/Response Flow

```
Test Method
    │
    ├─ Create Request Object
    │  (RestAssured.given())
    │
    ├─ Add Headers
    │  (Content-Type, Accept)
    │
    ├─ Add Body (if POST/PUT)
    │  (Serialized Model Object)
    │
    ├─ Execute HTTP Call
    │  (GET, POST, PUT, DELETE)
    │
    ├─ Receive Response
    │  (Status, Headers, Body)
    │
    ├─ Extract Data
    │  (Deserialize JSON to Model)
    │
    └─ Assert & Log
       (Validate Results)
```

---

## 🔐 Error Handling Strategy

### Request-Level Error Handling

```java
try {
    Response response = ApiRequestHelper.getRequest(url);
    // Handle response
} catch (RestClientException e) {
    logFailure("API connection error: " + e.getMessage());
    // Assertions will fail
}
```

### Assertion-Level Error Handling

```java
// Assertions provide clear failure messages
Assert.assertEquals(
    response.getStatusCode(), 
    200, 
    "Expected HTTP 200 OK but got " + response.getStatusCode()
);
```

### Graceful Degradation

```java
// Handle API-specific behaviors (e.g., FakeRestAPI leniency)
int statusCode = response.getStatusCode();
Assert.assertTrue(
    statusCode == 404 || statusCode == 200,
    "Expected HTTP 404 or 200"
);
```

---

## 📊 Test Classification

### By Scope

| Category | Coverage | Examples |
|----------|----------|----------|
| **Unit Tests** | Individual methods | Model validation |
| **Component Tests** | API endpoints | POST, GET, PUT, DELETE |
| **Integration Tests** | Multi-endpoint flows | Create → Read → Update → Delete |
| **E2E Tests** | Complete workflows | Full book lifecycle |

### By Execution

```
Happy Path Tests (70% of suite)
├─ Standard operations
├─ Expected inputs
└─ Expected outputs

Edge Case Tests (30% of suite)
├─ Boundary conditions
├─ Invalid inputs
├─ Error scenarios
└─ Performance limits
```

---

## 🏆 SOLID Principles Implementation

### Single Responsibility Principle (SRP)

Each class has one reason to change:

```
ApiConfig       → Only configuration changes
ApiRequestHelper → Only request mechanism changes
AssertionHelper → Only assertion logic changes
Book/Author    → Only data structure changes
BaseTest       → Only test lifecycle changes
```

### Open/Closed Principle (OCP)

Classes are open for extension, closed for modification:

```java
// Can extend BaseTest without modifying it
public class BooksHappyPathTest extends BaseTest {
    // New test logic without changing BaseTest
}

// Can add new assertion helper methods without breaking existing ones
public class AssertionHelper {
    public static void assertCustom() { ... }  // New method
}
```

### Liskov Substitution Principle (LSP)

Subtypes can substitute base types:

```java
// Any test class can substitute BaseTest
BaseTest test = new BooksHappyPathTest();
test.setUp();  // Works the same way
```

### Interface Segregation Principle (ISP)

Clients depend only on methods they use:

```java
// ApiRequestHelper provides only API request methods
// AssertionHelper provides only assertion methods
// No bloated interfaces
```

### Dependency Inversion Principle (DIP)

Depend on abstractions, not concretions:

```java
// Tests depend on helper methods (abstractions)
ApiRequestHelper.getRequest(url);  // Implementation hidden
// Not directly on RestAssured (concretions)
```

---

## 🔍 Testing Strategy

### Test Pyramid

```
        △
       /│\
      / │ \
     /  │  \         E2E Tests (5%)
    /   │   \        - Complex workflows
   ┌────┼────┐
   │    │    │       Integration Tests (25%)
   │ Component Tests  - Multiple endpoints
   │    │    │       
   ├────┼────┤
   │ Unit Tests      (70%)
   │ - Individual methods
   │ - Model validation
   │ - Simple assertions
   └────┴────┘
```

### Coverage Metrics

```
Code Coverage Target: 85%+
├─ Models: 100%
├─ Config: 100%
├─ Helpers: 95%+
└─ Tests: 80%+

Test Coverage Target: 95%+
├─ Books API Endpoints: 100% (5/5)
├─ Authors API Endpoints: 100% (5/5)
├─ Happy Path: 95%+ pass rate
└─ Edge Cases: 85%+ coverage
```

---

## 📈 Extensibility Points

### Adding New Test Suites

1. Create new package in `tests/` directory
2. Extend `BaseTest` class
3. Use existing helpers (ApiRequestHelper, AssertionHelper)
4. Add test cases with @Test annotation
5. Update `testng.xml` to include new tests

Example:
```java
package tests.inventory;

public class InventoryHappyPathTest extends BaseTest {
    @Test
    public void testGetInventory() {
        // Use existing helpers
    }
}
```

### Adding New Endpoints

1. Add endpoint constant in `ApiConfig`
2. Create corresponding model class
3. Create test classes extending BaseTest
4. Use existing request and assertion helpers

Example:
```java
// In ApiConfig.java
public static final String INVENTORY_ENDPOINT = API_V1 + "/Inventory";

// In models/Inventory.java
public class Inventory { ... }

// In tests/inventory/InventoryTests.java
public class InventoryTests extends BaseTest { ... }
```

### Adding Custom Assertions

```java
// In AssertionHelper.java
public static void assertCustomCondition(Response response, String message) {
    // Custom assertion logic
}
```

---

## 🔧 Configuration Management

### Environment-Specific Configuration

```java
// Can be extended to support multiple environments
public class ApiConfig {
    private static final String ENV = System.getenv("API_ENV");
    
    public static String getBaseUrl() {
        return switch(ENV) {
            case "DEV" -> "https://dev-api.example.com";
            case "STAGE" -> "https://stage-api.example.com";
            default -> "https://fakerestapi.azurewebsites.net";
        };
    }
}
```

### Property-Based Configuration

```java
// Can load from properties file
private static final Properties props = new Properties();

static {
    try {
        props.load(ClassLoader.getResource("api.properties").openStream());
    } catch (IOException e) {
        // Handle error
    }
}
```

---

## 📝 Documentation Standards

Every class includes:
- **Class Documentation** - Purpose and usage
- **Method Documentation** - Parameters, return, behavior
- **Example Usage** - How to use the class
- **Related Classes** - Dependencies and relationships

Example:
```java
/**
 * API Request Helper Class
 * Provides reusable methods for making API requests
 * Follows DRY (Don't Repeat Yourself) principle
 * 
 * Usage:
 * Response response = ApiRequestHelper.getRequest(url);
 * Response response = ApiRequestHelper.postRequest(url, body);
 * 
 * @author QA Team
 * @version 1.0
 * @since 2024-02-08
 */
public class ApiRequestHelper { ... }
```

---

## 🚀 Performance Considerations

### Request Optimization
- Reuse request specifications
- Minimize payload size
- Use appropriate timeouts
- Implement connection pooling

### Test Execution Optimization
- Run independent tests in parallel
- Use appropriate wait strategies
- Minimize database/API calls
- Cache frequently used data

### Report Generation
- Generate reports asynchronously
- Archive old reports
- Optimize report size
- Use efficient serialization

---

## 🔐 Security Considerations

### API Communication
- Use HTTPS for all requests
- Validate SSL certificates
- Implement timeout mechanisms
- Sanitize sensitive data in logs

### Test Data
- Use non-sensitive test data
- Implement data masking for reports
- Clear test data after execution
- Secure credentials in CI/CD

### Code Security
- No hardcoded credentials
- Secure dependency management
- Regular dependency updates
- Code review processes

---

**Framework Version:** 1.0  
**Last Updated:** February 8, 2024  
**Maintainer:** API QA Team


