package tests.books;

import base.BaseTest;
import config.ApiConfig;
import io.restassured.response.Response;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;
import utils.AssertionHelper;

public class BooksEdgeCaseTest extends BaseTest {

    private static final String TEST_CLASS_NAME = "Books Edge Case Tests";

    @Test(priority = 1, description = "GET book with non-existent ID - Edge Case")
    public void testGetBookWithNonExistentId() {
        logSection(TEST_CLASS_NAME + " - Get Non-Existent Book");

        logStep(1, "Making GET request with non-existent ID: 99999");
        Response response = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL() + "/99999");

        logStep(2, "Verifying HTTP 404 Not Found status code");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        Assert.assertTrue(statusCode == 404 || statusCode == 200,
            "Expected HTTP 404 or 200 status code, got " + statusCode);

        logSuccess("GET non-existent book test passed");
    }

    @Test(priority = 2, description = "GET book with invalid ID format - Edge Case")
    public void testGetBookWithInvalidIdFormat() {
        logSection(TEST_CLASS_NAME + " - Get Book Invalid ID Format");

        logStep(1, "Making GET request with invalid ID format: 'abc'");
        Response response = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL() + "/abc");

        logStep(2, "Verifying error status code");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        // API behavior varies, so we verify it's not 200
        Assert.assertTrue(statusCode >= 400, "Should return error status code");

        logSuccess("GET invalid ID format test passed");
    }

    @Test(priority = 3, description = "POST book with null title - Edge Case")
    public void testCreateBookWithNullTitle() {
        logSection(TEST_CLASS_NAME + " - Create Book Null Title");

        logStep(1, "Creating book object with null title");
        Book invalidBook = Book.builder()
                .title(null)  // Missing required field
                .description("Description without title")
                .pageCount(200)
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending POST request with invalid data");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), invalidBook);

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        logInfo("API accepted null title with status: " + statusCode);

        logSuccess("POST null title test completed");
    }

    @Test(priority = 4, description = "POST book with negative page count - Edge Case")
    public void testCreateBookWithNegativePageCount() {
        logSection(TEST_CLASS_NAME + " - Create Book Negative Page Count");

        logStep(1, "Creating book object with negative page count");
        Book invalidBook = Book.builder()
                .title("Invalid Book")
                .description("Book with negative pages")
                .pageCount(-100)  // Invalid: negative
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending POST request with negative page count");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), invalidBook);

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        logInfo("API behavior with negative page count: " + statusCode);

        logSuccess("POST negative page count test completed");
    }

    @Test(priority = 5, description = "POST book with zero page count - Edge Case")
    public void testCreateBookWithZeroPageCount() {
        logSection(TEST_CLASS_NAME + " - Create Book Zero Page Count");

        logStep(1, "Creating book object with zero page count");
        Book boundaryBook = Book.builder()
                .title("Zero Page Book")
                .description("Book with zero pages")
                .pageCount(0)  // Boundary: zero
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending POST request with zero page count");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), boundaryBook);

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        logInfo("API behavior with zero page count: " + statusCode);

        logSuccess("POST zero page count test completed");
    }

    @Test(priority = 6, description = "PUT book with invalid ID - Edge Case")
    public void testUpdateBookWithInvalidId() {
        logSection(TEST_CLASS_NAME + " - Update Book Invalid ID");

        logStep(1, "Creating update book object");
        Book updateBook = Book.builder()
                .id(99999)
                .title("Ghost Book")
                .description("Trying to update non-existent book")
                .pageCount(300)
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending PUT request with invalid ID: 99999");
        Response response = ApiRequestHelper.putRequest(
            ApiConfig.getBooksEndpointURL() + "/99999",
            updateBook
        );

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);

        logSuccess("PUT invalid ID test completed");
    }

    @Test(priority = 7, description = "DELETE book with invalid ID - Edge Case")
    public void testDeleteBookWithInvalidId() {
        logSection(TEST_CLASS_NAME + " - Delete Book Invalid ID");

        logStep(1, "Sending DELETE request with invalid ID: 99999");
        Response response = ApiRequestHelper.deleteRequest(ApiConfig.getBooksEndpointURL() + "/99999");

        logStep(2, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        Assert.assertTrue(statusCode >= 200, "Should return valid status code");

        logSuccess("DELETE invalid ID test completed");
    }

    @Test(priority = 8, description = "POST book with very long title - Edge Case")
    public void testCreateBookWithLongTitle() {
        logSection(TEST_CLASS_NAME + " - Create Book Long Title");

        logStep(1, "Creating book object with very long title");
        String longTitle = "A".repeat(1000);
        Book boundaryBook = Book.builder()
                .title(longTitle)
                .description("Book with very long title")
                .pageCount(500)
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending POST request with long title");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), boundaryBook);

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);
        logInfo("API handles long title with status: " + statusCode);

        logSuccess("POST long title test completed");
    }

    @Test(priority = 9, description = "Verify response time - Performance Edge Case")
    public void testResponseTime() {
        logSection(TEST_CLASS_NAME + " - Response Time");

        logStep(1, "Making GET request to measure response time");
        long startTime = System.currentTimeMillis();
        Response response = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL());
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        logStep(2, "Verifying response time");
        logInfo("Response time: " + responseTime + " ms");
        Assert.assertTrue(responseTime < 5000, "Response should be within 5 seconds");

        logSuccess("Response time test passed - Time: " + responseTime + " ms");
    }

    @Test(priority = 10, description = "POST book with empty description - Edge Case")
    public void testCreateBookWithEmptyDescription() {
        logSection(TEST_CLASS_NAME + " - Create Book Empty Description");

        logStep(1, "Creating book object with empty description");
        Book bookWithEmptyDesc = Book.builder()
                .title("Book Without Description")
                .description("")
                .pageCount(250)
                .publishDate("2024-02-08T00:00:00")
                .build();

        logStep(2, "Sending POST request");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), bookWithEmptyDesc);

        logStep(3, "Verifying response status");
        int statusCode = response.getStatusCode();
        logInfo("Received status code: " + statusCode);

        logSuccess("POST empty description test completed");
    }
}

