# API Automation Testing Framework - FakeRestAPI Bookstore

A professional, enterprise-grade API automation testing framework built with **Java**, **RestAssured**, and **TestNG**. This framework tests the FakeRestAPI Bookstore API with comprehensive coverage of happy paths, edge cases, and error scenarios.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [Prerequisites](#prerequisites)
5. [Installation & Setup](#installation--setup)
6. [Running Tests](#running-tests)
7. [Test Coverage](#test-coverage)
8. [CI/CD Integration](#cicd-integration)
9. [Reports & Artifacts](#reports--artifacts)
10. [Code Quality & Best Practices](#code-quality--best-practices)
11. [Troubleshooting](#troubleshooting)
12. [Contributing](#contributing)

---

## 🎯 Project Overview

This project automates testing of a RESTful API for an online bookstore. It includes:

- **20+ Test Cases** covering happy paths and edge cases
- **Books API** - Full CRUD operations
- **Authors API** - Complete endpoint coverage (Bonus)
- **Professional Reporting** with Allure Reports
- **CI/CD Integration** with GitHub Actions and Jenkins
- **Clean Architecture** following SOLID principles
- **Detailed Logging** for debugging and audit trails

### API Endpoints Tested

**Books API:**
- `GET /api/v1/Books` - Retrieve all books
- `GET /api/v1/Books/{id}` - Retrieve specific book
- `POST /api/v1/Books` - Create new book
- `PUT /api/v1/Books/{id}` - Update book
- `DELETE /api/v1/Books/{id}` - Delete book

**Authors API (Bonus):**
- `GET /api/v1/Authors` - Retrieve all authors
- `GET /api/v1/Authors/{id}` - Retrieve specific author
- `POST /api/v1/Authors` - Create new author
- `PUT /api/v1/Authors/{id}` - Update author
- `DELETE /api/v1/Authors/{id}` - Delete author

---

## ✨ Features

- ✅ **Clean Code Architecture** - Organized with config, models, utils, tests packages
- ✅ **Reusable Components** - Helper classes for API requests and assertions
- ✅ **Comprehensive Test Cases** - Happy paths, edge cases, boundary conditions
- ✅ **Professional Reporting** - Allure Reports with detailed test metrics
- ✅ **CI/CD Ready** - GitHub Actions and Jenkins pipeline configurations
- ✅ **Logging & Monitoring** - SLF4J logging with detailed test steps
- ✅ **Data Models** - POJO classes with validation methods
- ✅ **SOLID Principles** - Single Responsibility, DRY code, maintainable structure
- ✅ **Performance Testing** - Response time validation
- ✅ **Error Handling** - Comprehensive edge case coverage

---

## 📁 Project Structure

```
RestAssuredDemo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── Main.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java                 # Base test class with common setup
│       │   ├── config/
│       │   │   └── ApiConfig.java               # Centralized API configuration
│       │   ├── models/
│       │   │   ├── Book.java                    # Book data model
│       │   │   └── Author.java                  # Author data model
│       │   ├── utils/
│       │   │   ├── ApiRequestHelper.java        # API request utilities
│       │   │   └── AssertionHelper.java         # Custom assertion methods
│       │   └── tests/
│       │       ├── books/
│       │       │   ├── BooksHappyPathTest.java  # Happy path tests (5 cases)
│       │       │   └── BooksEdgeCaseTest.java   # Edge case tests (10 cases)
│       │       └── authors/
│       │           └── AuthorsHappyPathTest.java # Authors API tests (5 cases)
│       └── resources/
│           └── testng.xml                       # TestNG configuration
├── .github/
│   └── workflows/
│       └── ci-cd-pipeline.yml                   # GitHub Actions pipeline
├── Jenkinsfile                                  # Jenkins pipeline configuration
├── pom.xml                                      # Maven dependencies & plugins
└── README.md                                    # This file
```

### Folder Descriptions

| Folder | Purpose |
|--------|---------|
| `base/` | Base test classes with common setup, teardown, logging |
| `config/` | API configuration, endpoints, constants |
| `models/` | Data models (Book, Author) with validation methods |
| `utils/` | Helper classes for API requests and assertions |
| `tests/` | Test cases organized by API endpoint |
| `.github/workflows/` | GitHub Actions CI/CD pipeline |

---

## 📋 Prerequisites

### System Requirements
- **Java JDK 21** or higher
- **Maven 3.9.0** or higher
- **Git** (for version control)

### Required Software
```bash
# Check Java installation
java -version

# Check Maven installation
mvn --version
```

### Libraries & Dependencies
All dependencies are managed by Maven in `pom.xml`:
- **RestAssured** - API testing library
- **TestNG** - Test framework
- **Hamcrest** - Assertion library
- **Jackson** - JSON processing
- **Allure** - Report generation
- **Lombok** - Code reduction
- **SLF4J** - Logging framework

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd RestAssuredDemo
```

### Step 2: Install Dependencies

```bash
# Download all Maven dependencies
mvn clean install

# Expected output: "BUILD SUCCESS"
```

### Step 3: Verify Installation

```bash
# Check project structure
mvn clean compile

# Expected output: "[INFO] BUILD SUCCESS"
```

### Step 4: Optional - Install Allure CLI (for local report generation)

**Windows:**
```bash
# Using Chocolatey
choco install allure

# Or manually download from https://docs.qameta.io/allure/
```

**Mac:**
```bash
brew install allure
```

**Linux:**
```bash
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure
```

---

## ▶️ Running Tests

### Option 1: Run All Tests

```bash
# Clean, compile, and run all tests
mvn clean test

# Expected: All 20 test cases should execute
```

### Option 2: Run Specific Test Class

```bash
# Run only Books Happy Path tests
mvn test -Dtest=BooksHappyPathTest

# Run only Books Edge Case tests
mvn test -Dtest=BooksEdgeCaseTest

# Run only Authors tests
mvn test -Dtest=AuthorsHappyPathTest
```

### Option 3: Run Tests with TestNG Suite

```bash
# Run tests using testng.xml configuration
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Option 4: Run Tests from IDE

**Using IntelliJ IDEA:**
1. Right-click on test class → **Run 'TestClassName'**
2. Right-click on entire test folder → **Run 'java'**
3. Right-click on `testng.xml` → **Run**

**Using Eclipse:**
1. Right-click on test class → **Run As** → **TestNG Test**

### Option 5: Run Tests with Maven Profiles

```bash
# Run with custom parameters
mvn test -Dtest=BooksHappyPathTest -X

# -X flag enables debug logging
```

### Example Test Execution

```bash
$ mvn clean test

[INFO] -------------------------------------------------------
[INFO] T E S T S
[INFO] -------------------------------------------------------
[INFO] Running tests.books.BooksHappyPathTest
[INFO] ═══════════════════════════════════════════════════════
[INFO] Starting Test Class: BooksHappyPathTest
[INFO] ═══════════════════════════════════════════════════════
[INFO] 
[INFO] ─────────────────────────────────────────────────────────
[INFO] TEST: Books Happy Path Tests - Get All Books
[INFO] ─────────────────────────────────────────────────────────
[INFO] Step 1: Making GET request to retrieve all books
[INFO] Making GET request to: https://fakerestapi.azurewebsites.net/api/v1/Books
[INFO] Step 2: Verifying HTTP 200 status code
[INFO] ✓ Status code 200 assertion passed
[INFO] ✓ SUCCESS: GET all books test passed
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 12.345 sec

[INFO] BUILD SUCCESS
```

---

## 📊 Test Coverage

### Test Statistics

| Category | Count | Details |
|----------|-------|---------|
| **Happy Path Tests** | 10 | 5 Books + 5 Authors |
| **Edge Case Tests** | 10 | Non-existent IDs, invalid formats, boundary values |
| **Total Test Cases** | 20 | ~95% endpoint coverage |
| **Pass Rate Goal** | 95%+ | Accounts for API behavioral variations |

### Test Case Breakdown

#### Books Happy Path Tests (5 tests)
1. **testGetAllBooks** - Retrieve all books with valid GET request
2. **testGetBookById** - Retrieve specific book with valid ID
3. **testCreateNewBook** - Create new book with valid data
4. **testUpdateBook** - Update existing book with new data
5. **testDeleteBook** - Delete book and verify deletion

#### Books Edge Case Tests (10 tests)
1. **testGetBookWithNonExistentId** - Handle non-existent book ID
2. **testGetBookWithInvalidIdFormat** - Handle malformed ID format
3. **testCreateBookWithNullTitle** - Validate missing required field
4. **testCreateBookWithNegativePageCount** - Handle negative boundary value
5. **testCreateBookWithZeroPageCount** - Handle zero boundary value
6. **testUpdateBookWithInvalidId** - Update non-existent book
7. **testDeleteBookWithInvalidId** - Delete non-existent book
8. **testCreateBookWithLongTitle** - Handle large input (1000 chars)
9. **testResponseTime** - Verify response time performance
10. **testCreateBookWithEmptyDescription** - Handle empty optional field

#### Authors Happy Path Tests (5 tests)
1. **testGetAllAuthors** - Retrieve all authors
2. **testGetAuthorById** - Retrieve specific author
3. **testCreateNewAuthor** - Create new author
4. **testUpdateAuthor** - Update existing author
5. **testDeleteAuthor** - Delete author

---

## 🔄 CI/CD Integration

### GitHub Actions Pipeline

**File:** `.github/workflows/ci-cd-pipeline.yml`

**Features:**
- Automated testing on push and pull requests
- Daily scheduled test runs
- Automatic test report generation
- Artifact uploads
- Test result publishing

**How to Set Up:**

1. Push code to GitHub repository
2. GitHub Actions automatically triggers on:
   - Push to `main` or `develop` branches
   - Pull requests to `main` or `develop`
   - Daily at 2 AM UTC (scheduled)

3. View results:
   - Navigate to **Actions** tab in GitHub
   - Click on workflow run
   - View logs, reports, and artifacts

**Example Workflow Status:**
```
✓ Checkout Code
✓ Set up Java JDK 21
✓ Display Java and Maven Versions
✓ Build Project with Maven
✓ Run API Automation Tests
✓ Generate Allure Report
✓ Upload Artifacts
```

### Jenkins Pipeline

**File:** `Jenkinsfile`

**Features:**
- Multi-stage pipeline (Build, Test, Report, Publish)
- Parameterized test suite selection
- Timeout management
- Email notifications
- Test result archiving

**How to Set Up:**

1. **Create Jenkins Job:**
   ```
   New Item → Pipeline → OK
   Definition: Pipeline script from SCM
   SCM: Git
   Repository: <your-repo-url>
   Script Path: Jenkinsfile
   ```

2. **Configure Build Parameters:**
   - TEST_SUITE: all, books-happy, books-edge, authors
   - TIMEOUT_MINUTES: 15

3. **Run Pipeline:**
   - Click "Build Now" or configure webhook triggers

4. **View Results:**
   - Console Output for logs
   - Test Results for metrics
   - Artifacts for reports

---

## 📈 Reports & Artifacts

### Allure Report Generation

```bash
# Generate Allure report
mvn allure:report

# Open Allure report in browser
mvn allure:serve
```

**Report includes:**
- Test execution timeline
- Pass/fail statistics
- Test severity levels
- Failure analysis
- Trend charts
- Step-by-step test flow

### Available Artifacts

After test execution, artifacts are available:
- **allure-report/** - Detailed HTML report
- **target/surefire-reports/** - TestNG XML reports
- **target/site/allure-maven-plugin/** - Allure artifacts

### Example Report Structure

```
Allure Report
├── Overview
│   ├── Test Statistics (20 total, 19 passed, 1 failed)
│   ├── Duration (12.5 seconds)
│   └── Trend Chart
├── Test Cases
│   ├── Books Tests
│   │   ├── Happy Path (5/5 passed)
│   │   └── Edge Cases (10/10 passed)
│   └── Authors Tests (5/5 passed)
├── Failures
│   └── Detailed failure analysis
└── Timeline
    └── Execution flow with timings
```

---

## 🏗️ Code Quality & Best Practices

### SOLID Principles Applied

1. **Single Responsibility Principle**
   - `ApiConfig` - Only manages configuration
   - `ApiRequestHelper` - Only handles API requests
   - `AssertionHelper` - Only handles assertions

2. **Open/Closed Principle**
   - Models are extensible without modification
   - Helper classes can be extended for new functionality

3. **Liskov Substitution Principle**
   - Base test class can be substituted by any test class

4. **Interface Segregation Principle**
   - Helper classes provide focused, single-purpose methods

5. **Dependency Inversion Principle**
   - Tests depend on abstractions (helper methods)
   - Low coupling with actual API implementation

### Code Quality Practices

| Practice | Implementation |
|----------|----------------|
| **DRY (Don't Repeat Yourself)** | Helper classes reduce code duplication |
| **KISS (Keep It Simple)** | Clear naming, simple logic, minimal complexity |
| **Clean Code** | Meaningful names, small methods, proper formatting |
| **Documentation** | Comprehensive JavaDoc comments |
| **Logging** | Detailed logs with SLF4J |
| **Error Handling** | Try-catch blocks, graceful failures |
| **Configuration Management** | Centralized config in ApiConfig.java |

### Code Organization

```
✓ Package structure follows Java conventions
✓ One class per file
✓ Clear naming: <Functionality><Pattern> (e.g., BooksHappyPathTest)
✓ Methods are focused and single-purpose
✓ Comments explain the "why", not the "what"
✓ Logging statements at appropriate levels (INFO, DEBUG, ERROR)
```

---

## 🔍 Detailed Test Documentation

### Example: testGetAllBooks()

```java
/**
 * Test Case: GET all books
 * 
 * OBJECTIVE:
 * Retrieve all books from the API and verify the response
 * 
 * PRECONDITIONS:
 * - API is accessible
 * - Database contains at least one book
 * 
 * TEST STEPS:
 * 1. Make GET request to /api/v1/Books
 * 2. Verify HTTP 200 status code
 * 3. Validate response contains book array
 * 4. Verify book fields (id, title, etc.)
 * 
 * EXPECTED RESULTS:
 * - HTTP 200 OK status
 * - Response body contains valid JSON
 * - Books array is not empty
 * - Each book has required fields
 * 
 * POST CONDITIONS:
 * - No data modifications
 * - API state unchanged
 */
```

### Example: testGetBookWithNonExistentId()

```java
/**
 * Test Case: GET book with non-existent ID (Edge Case)
 * 
 * OBJECTIVE:
 * Verify API behavior when requesting a non-existent resource
 * 
 * TEST STEPS:
 * 1. Make GET request with ID 99999 (non-existent)
 * 2. Capture response status code
 * 3. Verify error handling
 * 
 * EXPECTED RESULTS:
 * - HTTP 404 (Not Found) or 200 with empty response
 * - No exception thrown
 * - Graceful error handling
 * 
 * EDGE CASE TYPE:
 * - Invalid resource reference
 * - Boundary condition
 */
```

---

## 🐛 Troubleshooting

### Issue 1: Tests Cannot Find Class

**Error:**
```
[ERROR] error: cannot find symbol: class BooksHappyPathTest
```

**Solution:**
```bash
# Rebuild project
mvn clean compile

# Ensure source folders are correctly configured in IDE
# Check: Project Structure → Modules → Sources
```

### Issue 2: API Connection Timeout

**Error:**
```
[ERROR] Connection refused: Unable to reach https://fakerestapi.azurewebsites.net
```

**Solution:**
1. Verify internet connection
2. Check if FakeRestAPI is online: https://fakerestapi.azurewebsites.net/api/v1/Books
3. Increase timeout in `ApiConfig.java`:
   ```java
   public static final long REQUEST_TIMEOUT = 10000;  // 10 seconds
   ```

### Issue 3: Allure Report Not Generating

**Error:**
```
[ERROR] Allure report generation failed
```

**Solution:**
```bash
# Install Allure CLI
brew install allure  # macOS
# or
choco install allure  # Windows

# Clear cache and rebuild
mvn clean allure:report

# Serve report locally
mvn allure:serve
```

### Issue 4: Tests Pass Locally but Fail in CI/CD

**Solution:**
1. Check environment variables
2. Verify Java version compatibility
3. Check network/firewall issues in CI/CD environment
4. Review CI/CD logs for specific errors
5. Ensure `testng.xml` is properly configured

### Issue 5: Port Already in Use (Jenkins)

**Error:**
```
java.net.BindException: Address already in use
```

**Solution:**
```bash
# Find process using the port
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change Jenkins port in Jenkinsfile
```

---

## 📚 Additional Resources

### Documentation Links
- [RestAssured Documentation](https://rest-assured.io)
- [TestNG Documentation](https://testng.org)
- [Allure Report](https://docs.qameta.io/allure)
- [FakeRestAPI](https://fakerestapi.azurewebsites.net)

### Related Projects
- [Java Testing Best Practices](https://www.baeldung.com/java-test)
- [API Testing Strategies](https://www.testng.org)

---

## 🤝 Contributing

### How to Contribute

1. **Create a feature branch:**
   ```bash
   git checkout -b feature/new-api-tests
   ```

2. **Make your changes:**
   - Add new test cases in appropriate packages
   - Update documentation
   - Follow existing code style

3. **Commit with clear messages:**
   ```bash
   git commit -m "Add: New test cases for Books API"
   ```

4. **Push and create pull request:**
   ```bash
   git push origin feature/new-api-tests
   ```

### Code Style Guide

- Follow Java naming conventions (camelCase)
- Use meaningful variable names
- Add JavaDoc comments for public methods
- Keep methods focused and single-purpose
- Add logging at appropriate levels

---

## 📄 License

This project is provided as-is for educational and commercial purposes.

---

## ✅ Checklist for Running Tests

Use this checklist to ensure proper setup:

- [ ] Java 21 installed and configured
- [ ] Maven 3.9.0 installed
- [ ] Repository cloned locally
- [ ] `mvn clean install` executed successfully
- [ ] No compilation errors (`mvn clean compile`)
- [ ] Test configuration in `testng.xml` verified
- [ ] API endpoint accessible (check URL in browser)
- [ ] Tests run successfully (`mvn test`)
- [ ] Reports generated (`mvn allure:report`)
- [ ] CI/CD pipeline configured (GitHub Actions / Jenkins)


