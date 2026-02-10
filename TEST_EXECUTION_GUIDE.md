# Test Execution Guide

A comprehensive guide for executing the API Automation tests with detailed step-by-step instructions.

---

## 📋 Table of Contents

1. [Pre-Execution Checklist](#pre-execution-checklist)
2. [Test Execution Methods](#test-execution-methods)
3. [Execution Scenarios](#execution-scenarios)
4. [Interpreting Test Results](#interpreting-test-results)
5. [Debugging Failed Tests](#debugging-failed-tests)
6. [Performance Testing](#performance-testing)
7. [CI/CD Execution](#cicd-execution)

---

## ✅ Pre-Execution Checklist

Before running tests, verify the following:

### System Requirements
- [ ] Java JDK 21 installed and accessible
- [ ] Maven 3.9.0 or higher installed
- [ ] Git configured (if cloning from repository)
- [ ] Internet connection available
- [ ] FakeRestAPI endpoint is accessible

### Environment Setup
```bash
# Verify Java installation
java -version
# Expected: java version "21" or higher

# Verify Maven installation
mvn --version
# Expected: Apache Maven 3.9.0 or higher

# Verify project structure
ls -la
# Expected: pom.xml, src/, README.md, etc.
```

### IDE Configuration (if using IDE)
- [ ] Project is imported as Maven project
- [ ] Source folders are correctly configured
- [ ] Maven is configured as build tool
- [ ] JDK 21 is set as project SDK

### API Accessibility
```bash
# Verify FakeRestAPI is accessible
curl https://fakerestapi.azurewebsites.net/api/v1/Books

# Expected: HTTP 200 with JSON response
```

### Dependencies Installation
```bash
# Download all dependencies
mvn clean install

# Expected output:
# [INFO] BUILD SUCCESS
```

---

## 🎯 Test Execution Methods

### Method 1: Using Maven Command Line

#### 1.1 Run All Tests

```bash
# Clean, compile, and execute all tests
mvn clean test

# Output example:
# [INFO] -------------------------------------------------------
# [INFO] T E S T S
# [INFO] -------------------------------------------------------
# [INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

**Duration:** ~15-20 seconds  
**Coverage:** All 20 test cases  
**Reports:** Default TestNG report

#### 1.2 Run Specific Test Class

```bash
# Run Books Happy Path tests only
mvn test -Dtest=BooksHappyPathTest

# Run Books Edge Case tests only
mvn test -Dtest=BooksEdgeCaseTest

# Run Authors tests only
mvn test -Dtest=AuthorsHappyPathTest

# Run multiple specific classes
mvn test -Dtest=BooksHappyPathTest,AuthorsHappyPathTest
```

**Duration:** ~5-8 seconds per class  
**Coverage:** Single test class only

#### 1.3 Run Specific Test Method

```bash
# Run single test method
mvn test -Dtest=BooksHappyPathTest#testGetAllBooks

# Run multiple specific methods
mvn test -Dtest=BooksHappyPathTest#testGetAllBooks+testCreateNewBook
```

**Duration:** ~1-2 seconds per method  
**Coverage:** Single test only

#### 1.4 Run with TestNG Suite

```bash
# Execute tests using testng.xml configuration
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# This respects test grouping and execution order defined in testng.xml
```

**Duration:** ~15-20 seconds  
**Coverage:** All tests in specified suite

### Method 2: Using IDE (IntelliJ IDEA)

#### 2.1 Run All Tests

1. Navigate to **src/test/java**
2. Right-click on **java** folder
3. Select **Run 'java'**

Or use keyboard shortcut:
```
Ctrl + Shift + F10 (Windows)
Cmd + Shift + R (Mac)
```

#### 2.2 Run Test Class

1. Open test class file
2. Right-click on class name
3. Select **Run 'ClassName'**

Or click the green play icon next to class definition.

#### 2.3 Run Single Test Method

1. Open test class file
2. Find test method
3. Click green play icon next to @Test annotation
4. Select **Run 'testMethodName'**

#### 2.4 Run TestNG Suite

1. Right-click on **testng.xml**
2. Select **Run testng.xml**

Or:
1. Open **testng.xml**
2. Click green play icon at top-right

#### 2.5 View Test Results in IDE

```
Run Results Panel
├─ All Tests (20 total)
│  ├─ BooksHappyPathTest
│  │  ├─ ✓ testGetAllBooks
│  │  ├─ ✓ testGetBookById
│  │  ├─ ✓ testCreateNewBook
│  │  ├─ ✓ testUpdateBook
│  │  └─ ✓ testDeleteBook
│  ├─ BooksEdgeCaseTest
│  │  └─ ... (10 tests)
│  └─ AuthorsHappyPathTest
│     └─ ... (5 tests)
│
└─ Test Results:
   ├─ Duration: 12.345 sec
   ├─ Success rate: 100%
   └─ Failed tests: 0
```

### Method 3: Using IDE (Eclipse)

#### 3.1 Run All Tests

1. Right-click on **src/test/java** folder
2. Select **Run As** → **TestNG Test**

#### 3.2 Run Test Class

1. Open test class
2. Right-click on class name
3. Select **Run As** → **TestNG Test**

#### 3.3 Run Single Test

1. Click on test method
2. Right-click → **Run As** → **TestNG Test**

#### 3.4 View TestNG Results

```
TestNG Results View
├─ Tree View (Default)
│  ├─ Books Happy Path (5/5 passed)
│  ├─ Books Edge Cases (10/10 passed)
│  └─ Authors (5/5 passed)
│
├─ Reporter View
│  └─ Shows pass/fail summary
│
└─ Console View
   └─ Shows test execution logs
```

### Method 4: Using Gradle (If Added)

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests BooksHappyPathTest

# Run with report
./gradlew test --info
```

---

## 📊 Execution Scenarios

### Scenario 1: Quick Smoke Test (5 minutes)

**Objective:** Verify API connectivity and basic functionality

```bash
# Run only critical happy path tests
mvn test -Dtest=BooksHappyPathTest#testGetAllBooks

# Expected:
# ✓ API is reachable
# ✓ Basic GET request works
# ✓ Response is valid JSON
```

**When to use:** Pre-deployment validation

### Scenario 2: Full Happy Path (10 minutes)

**Objective:** Verify all happy path scenarios work correctly

```bash
# Run all happy path tests
mvn test -Dtest=BooksHappyPathTest,AuthorsHappyPathTest

# Expected:
# ✓ All CRUD operations work
# ✓ Data is created, read, updated, deleted
# ✓ No exceptions thrown
```

**When to use:** Regression testing before release

### Scenario 3: Comprehensive Edge Cases (15 minutes)

**Objective:** Verify error handling and edge cases

```bash
# Run all edge case tests
mvn test -Dtest=BooksEdgeCaseTest

# Expected:
# ✓ API handles invalid inputs gracefully
# ✓ Proper error responses returned
# ✓ Performance within limits
```

**When to use:** Robustness validation

### Scenario 4: Full Test Suite with Reports (20 minutes)

**Objective:** Complete test execution with detailed reports

```bash
# Run all tests and generate reports
mvn clean test
mvn allure:report
mvn allure:serve

# Expected:
# ✓ All 20 tests executed
# ✓ Allure report generated
# ✓ Report opened in browser
```

**When to use:** Final validation before release

### Scenario 5: Continuous Integration (15 minutes)

**Objective:** Automated test execution in CI/CD pipeline

```bash
# Used in GitHub Actions / Jenkins
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
mvn allure:report

# Expected:
# ✓ All tests run in CI/CD environment
# ✓ Reports generated and archived
# ✓ Notifications sent to team
```

**When to use:** Automated builds on code push

### Scenario 6: Scheduled Daily Tests (Overnight)

**Objective:** Nightly regression testing

```bash
# Scheduled at 2 AM UTC via GitHub Actions
mvn clean test
mvn allure:report

# Expected:
# ✓ Tests run during off-hours
# ✓ Reports available next morning
# ✓ Trends tracked over time
```

**When to use:** Regular health checks

---

## 📈 Interpreting Test Results

### Success Criteria

**Test Pass:**
```
✓ testGetAllBooks PASSED
  Reason: Assertion succeeded
  Time: 1.234 sec
  Status Code: 200
```

**Test Fail:**
```
✗ testGetBookWithNonExistentId FAILED
  Reason: java.lang.AssertionError: Expected HTTP 404 but got 200
  Time: 0.456 sec
  Stack Trace: ...
```

### Common Test Outputs

#### All Tests Pass

```
-------------------------------------------------------
T E S T S
-------------------------------------------------------
Running tests.books.BooksHappyPathTest
Running tests.books.BooksEdgeCaseTest
Running tests.authors.AuthorsHappyPathTest

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

**Action:** Ready for release

#### Some Tests Fail

```
-------------------------------------------------------
T E S T S
-------------------------------------------------------
Tests run: 20, Failures: 2, Errors: 0, Skipped: 0

FAILURES:
1) testGetBookWithNonExistentId
   java.lang.AssertionError: Expected 404 but got 200
   
2) testCreateBookWithNullTitle
   java.lang.AssertionError: Expected 400 but got 201

BUILD FAILURE
```

**Action:** Investigate failures, check API behavior, update tests if needed

#### Tests Error Out

```
-------------------------------------------------------
T E S T S
-------------------------------------------------------
Tests run: 20, Failures: 0, Errors: 3, Skipped: 0

ERRORS:
1) testGetAllBooks
   java.net.ConnectException: Connection refused
   
BUILD FAILURE
```

**Action:** Check network, verify API is running, review environment

### Performance Metrics

```
Test Execution Summary
┌─────────────────────────────────────┐
│ Total Tests:        20              │
│ Passed:             20              │
│ Failed:              0              │
│ Skipped:             0              │
│ Success Rate:      100%             │
│ Total Duration:   12.5 sec          │
│ Avg Test Time:    0.625 sec         │
└─────────────────────────────────────┘

Test Suite Breakdown
┌──────────────────────────────────────┐
│ Books Happy Path:      5 tests (2.1s) │
│ Books Edge Cases:     10 tests (5.8s) │
│ Authors Happy Path:    5 tests (4.6s) │
└──────────────────────────────────────┘
```

### Log Analysis

**INFO Level Logs (Normal Execution):**
```
[INFO] Starting Test Class: BooksHappyPathTest
[INFO] Making GET request to: https://fakerestapi...
[INFO] ✓ Status code 200 assertion passed
[INFO] Step 1: Making GET request
[INFO] ✓ SUCCESS: GET all books test passed
```

**WARNING Level Logs (Potential Issues):**
```
[WARN] Response time 2.5 seconds exceeds 2 second warning threshold
[WARN] API returned unexpected status code for edge case
```

**ERROR Level Logs (Test Failures):**
```
[ERROR] ✗ FAILURE: API connection error
[ERROR] java.net.ConnectException: Connection refused
[ERROR] Stack trace...
```

---

## 🐛 Debugging Failed Tests

### Step 1: Read the Error Message

```
AssertionError: Expected HTTP 404 but got 200
├─ What: Expected status code doesn't match actual
├─ Expected: 404 (Not Found)
├─ Actual: 200 (OK)
└─ Action: API behavior changed or test assumption invalid
```

### Step 2: Check Test Logs

```bash
# Run test with debug logging
mvn test -Dtest=BooksHappyPathTest#testFailingTest -X

# Output includes:
# - Request details (URL, headers, body)
# - Response details (status, headers, body)
# - Assertion details
```

### Step 3: Verify API Accessibility

```bash
# Test API manually
curl -X GET "https://fakerestapi.azurewebsites.net/api/v1/Books" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json"

# Expected: HTTP 200 with book array JSON
```

### Step 4: Review Response Body

Add debugging to test:

```java
@Test
public void testFailingTest() {
    Response response = ApiRequestHelper.getRequest(url);
    
    // Print full response
    System.out.println("Status: " + response.getStatusCode());
    System.out.println("Body: " + response.getBody().asString());
    response.prettyPrint();
    
    // Then assert
    AssertionHelper.assertStatusCode200(response, "...");
}
```

### Step 5: Check Network/Firewall

```bash
# Test network connectivity
ping fakerestapi.azurewebsites.net

# Test DNS resolution
nslookup fakerestapi.azurewebsites.net

# Test with curl (detailed)
curl -v https://fakerestapi.azurewebsites.net/api/v1/Books
```

### Step 6: Isolate the Issue

**If API is down:**
```
❌ API Server
  ├─ Check FakeRestAPI status page
  ├─ Wait for API to come back online
  └─ Re-run tests
```

**If test logic is wrong:**
```
❌ Test Code
  ├─ Review test expectations
  ├─ Check API documentation
  ├─ Update test if behavior changed
  └─ Re-run test
```

**If environment is wrong:**
```
❌ Test Environment
  ├─ Verify network connectivity
  ├─ Check firewall rules
  ├─ Verify Java/Maven installation
  └─ Re-run tests
```

### Common Debugging Commands

```bash
# Run single failing test with debug output
mvn test -Dtest=BooksHappyPathTest#testFailingTest -X

# Run tests and keep detailed logs
mvn test -Dtest=BooksHappyPathTest -DlogFileAppender.file=test.log

# Run with increased timeout
mvn test -Dtest=BooksHappyPathTest -Dorg.slf4j.simpleLogger.log.org.apache.http.wire=DEBUG

# Skip compilation, just run tests
mvn test -DskipCompile

# Run tests without timeout
mvn test -Dorg.apache.maven.plugins:maven-surefire-plugin:3.0.0:test
```

---

## 🚀 Performance Testing

### Test Execution Time

```bash
# Measure total execution time
time mvn clean test

# Output:
# real    0m22.456s
# user    0m45.123s
# sys     0m3.210s
```

### Individual Test Timing

Tests automatically log execution time:

```
[INFO] testGetAllBooks ............. 0.789 sec
[INFO] testCreateNewBook ........... 1.234 sec
[INFO] testUpdateBook .............. 0.856 sec
[INFO] testDeleteBook .............. 0.645 sec
[INFO] Total: 20 tests in 12.5 sec
```

### Performance Optimization

```bash
# Run tests in parallel (limited by API rate limits)
mvn test -DsuiteXmlFile=src/test/resources/testng.xml \
  -DparallelTestClasses=true

# Skip report generation for faster execution
mvn test -DskipReports

# Use cached dependencies
mvn test -o  # Offline mode (if dependencies already downloaded)
```

---

## 🔄 CI/CD Execution

### GitHub Actions Execution

**Triggered by:**
- Push to main/develop branches
- Pull requests
- Scheduled daily at 2 AM UTC

**Example workflow:**
```
✓ Checkout Code
✓ Set up Java JDK 21
✓ Build Project (mvn clean compile)
✓ Run Tests (mvn test)
✓ Generate Reports (mvn allure:report)
✓ Upload Artifacts
✓ Deploy to GitHub Pages
```

**View results:**
1. Go to GitHub repository
2. Click **Actions** tab
3. Select latest workflow run
4. View logs and artifacts

### Jenkins Execution

**Trigger methods:**
- Manual build
- Webhook on code push
- Scheduled builds
- Parameterized builds

**Pipeline stages:**
```
Checkout → Build → Test → Report → Archive → Notify
```

**Access results:**
1. Jenkins Dashboard
2. Select job
3. Click latest build
4. View Console Output
5. View Test Results
6. Download Artifacts

### Manual CI/CD Simulation

To test CI/CD pipeline locally:

```bash
# Simulate GitHub Actions
mvn clean test
mvn allure:report

# Simulate Jenkins
mvn clean
mvn compile
mvn test
mvn allure:report
rm -rf target/surefire-reports  # Archive
```

---

## 📊 Test Report Analysis

### Allure Report Structure

```
Allure Report
├─ Overview
│  ├─ Status (20/20 passed)
│  ├─ Duration (12.5 sec)
│  └─ Trend
├─ Suites
│  ├─ Books Happy Path (5 passed)
│  ├─ Books Edge Cases (10 passed)
│  └─ Authors (5 passed)
├─ Tests
│  └─ Detailed test cases
├─ Failures
│  └─ Analysis of failures
└─ Timeline
   └─ Execution timeline
```

### Generating Reports

```bash
# Generate Allure report
mvn allure:report

# Serve report locally (opens browser)
mvn allure:serve

# Report location: target/site/allure-maven-plugin/index.html

# View TestNG report
open target/surefire-reports/index.html
```

---

## ⏰ Execution Time Estimates

| Scenario | Duration | Command |
|----------|----------|---------|
| Single test | 1-2 sec | `mvn test -Dtest=...#testMethod` |
| Single class | 5-8 sec | `mvn test -Dtest=BooksHappyPathTest` |
| Full suite | 15-20 sec | `mvn clean test` |
| With reports | 20-30 sec | `mvn clean test && mvn allure:report` |
| CI/CD pipeline | 25-40 sec | Full build, test, report, archive |

---

## ✅ Execution Checklist

- [ ] Maven clean successful
- [ ] Tests compiled without errors
- [ ] All tests executed
- [ ] Expected number of tests passed
- [ ] No unexpected skips
- [ ] Reports generated
- [ ] Artifacts uploaded (if CI/CD)
- [ ] Logs reviewed for errors
- [ ] Performance within acceptable limits
- [ ] No flaky tests detected

---

**Last Updated:** February 8, 2024  
**Framework Version:** 1.0


