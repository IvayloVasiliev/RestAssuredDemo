package tests.authors;

import base.BaseTest;
import config.ApiConfig;
import io.restassured.response.Response;
import models.Author;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;
import utils.AssertionHelper;

import java.util.List;

public class AuthorsHappyPathTest extends BaseTest {

    private static final String TEST_CLASS_NAME = "Authors Happy Path Tests";

    @Test(priority = 1, description = "GET all authors - Happy Path")
    public void testGetAllAuthors() {
        logSection(TEST_CLASS_NAME + " - Get All Authors");

        logStep(1, "Making GET request to retrieve all authors");
        Response response = ApiRequestHelper.getRequest(ApiConfig.getAuthorsEndpointURL());

        logStep(2, "Verifying HTTP 200 status code");
        AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

        logStep(3, "Validating response body contains authors");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("firstName") || responseBody.contains("id"),
            "Response should contain author information");

        logSuccess("GET all authors test passed");
    }

    @Test(priority = 2, description = "GET specific author by ID - Happy Path")
    public void testGetAuthorById() {
        logSection(TEST_CLASS_NAME + " - Get Author By ID");

        logStep(1, "Fetching all authors to get a valid ID");
        Response allAuthorsResponse = ApiRequestHelper.getRequest(ApiConfig.getAuthorsEndpointURL());
        List<Author> authors = allAuthorsResponse.jsonPath().getList("", Author.class);

        if (authors.size() > 0) {
            int validAuthorId = authors.get(0).getId();
            logInfo("Using author ID: " + validAuthorId);

            logStep(2, "Making GET request for author ID: " + validAuthorId);
            Response response = ApiRequestHelper.getRequest(ApiConfig.getAuthorsEndpointURL() + "/" + validAuthorId);

            logStep(3, "Verifying HTTP 200 status code");
            AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

            logStep(4, "Validating author details");
            Author author = response.as(Author.class);
            Assert.assertNotNull(author, "Author object should not be null");
            Assert.assertEquals(author.getId(), validAuthorId, "Author ID should match the requested ID");

            logSuccess("GET specific author test passed");
        } else {
            logInfo("No authors found in API");
        }
    }

    @Test(priority = 3, description = "POST create new author - Happy Path")
    public void testCreateNewAuthor() {
        logSection(TEST_CLASS_NAME + " - Create New Author");

        logStep(1, "Creating new author object");
        Author newAuthor = Author.builder()
                .firstName("John")
                .lastName("Doe")
                .idBook("1")
                .build();
        logInfo("New author: " + newAuthor.getFullName());

        logStep(2, "Sending POST request to create author");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getAuthorsEndpointURL(), newAuthor);

        logStep(3, "Verifying response status code");
        int statusCode = response.getStatusCode();
        logInfo("Response status code: " + statusCode);
        Assert.assertTrue(statusCode == 200 || statusCode == 201,
            "Expected HTTP 200 or 201 status code, got " + statusCode);

        logStep(4, "Validating response");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("firstName") || responseBody.contains("id"),
            "Response should contain author information");

        logSuccess("POST create author test passed");
    }

    @Test(priority = 4, description = "PUT update author - Happy Path")
    public void testUpdateAuthor() {
        logSection(TEST_CLASS_NAME + " - Update Author");

        logStep(1, "Fetching first author to get valid ID");
        Response allAuthorsResponse = ApiRequestHelper.getRequest(ApiConfig.getAuthorsEndpointURL());
        List<Author> authors = allAuthorsResponse.jsonPath().getList("", Author.class);

        if (authors.size() > 0) {
            int authorId = authors.get(0).getId();
            logInfo("Using author ID: " + authorId);

            logStep(2, "Creating updated author object");
            Author updatedAuthor = Author.builder()
                    .id(authorId)
                    .firstName("Jane")
                    .lastName("Smith")
                    .idBook("1")
                    .build();
            logInfo("Updated author name: " + updatedAuthor.getFullName());

            logStep(3, "Sending PUT request to update author");
            Response response = ApiRequestHelper.putRequest(
                ApiConfig.getAuthorsEndpointURL() + "/" + authorId,
                updatedAuthor
            );

            logStep(4, "Verifying HTTP 200 OK status code");
            AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

            logSuccess("PUT update author test passed");
        } else {
            logInfo("No authors found to update");
        }
    }

    @Test(priority = 5, description = "DELETE author - Happy Path")
    public void testDeleteAuthor() {
        logSection(TEST_CLASS_NAME + " - Delete Author");

        logStep(1, "Fetching an author to delete");
        Response allAuthorsResponse = ApiRequestHelper.getRequest(ApiConfig.getAuthorsEndpointURL());
        List<Author> authors = allAuthorsResponse.jsonPath().getList("", Author.class);

        if (authors.size() > 0) {
            int authorId = authors.get(0).getId();
            logInfo("Deleting author ID: " + authorId);

            logStep(2, "Sending DELETE request");
            Response response = ApiRequestHelper.deleteRequest(ApiConfig.getAuthorsEndpointURL() + "/" + authorId);

            logStep(3, "Verifying response status code");
            int statusCode = response.getStatusCode();
            Assert.assertTrue(statusCode == 200 || statusCode == 204,
                "Expected HTTP 200 or 204 status code, got " + statusCode);

            logSuccess("DELETE author test passed");
        } else {
            logInfo("No authors found to delete");
        }
    }
}

