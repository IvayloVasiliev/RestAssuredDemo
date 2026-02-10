package tests.books;

import base.BaseTest;
import config.ApiConfig;
import io.restassured.response.Response;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiRequestHelper;
import utils.AssertionHelper;

import java.util.List;

public class BooksHappyPathTest extends BaseTest {

    private static final String TEST_CLASS_NAME = "Books Happy Path Tests";

    @Test(priority = 1, description = "GET all books - Happy Path")
    public void testGetAllBooks() {
        logSection(TEST_CLASS_NAME + " - Get All Books");

        logStep(1, "Making GET request to retrieve all books");
        Response response = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL());

        logStep(2, "Verifying HTTP 200 status code");
        AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

        logStep(3, "Validating response body contains books");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("id"), "Response should contain book IDs");
        Assert.assertTrue(responseBody.contains("title"), "Response should contain book titles");

        logSuccess("GET all books test passed");
    }

    @Test(priority = 2, description = "GET specific book by ID - Happy Path")
    public void testGetBookById() {
        logSection(TEST_CLASS_NAME + " - Get Book By ID");

        logStep(1, "Fetching all books to get a valid ID");
        Response allBooksResponse = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL());
        List<Book> books = allBooksResponse.jsonPath().getList("", Book.class);
        Assert.assertTrue(books.size() > 0, "Books list should not be empty");
        int validBookId = books.get(0).getId();
        logInfo("Using book ID: " + validBookId);

        logStep(2, "Making GET request for book ID: " + validBookId);
        Response response = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL() + "/" + validBookId);

        logStep(3, "Verifying HTTP 200 status code");
        AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

        logStep(4, "Validating book details");
        Book book = response.as(Book.class);
        Assert.assertNotNull(book, "Book object should not be null");
        Assert.assertEquals(book.getId(), validBookId, "Book ID should match the requested ID");
        Assert.assertNotNull(book.getTitle(), "Book title should not be null");

        logSuccess("GET specific book test passed");
    }

    @Test(priority = 3, description = "POST create new book - Happy Path")
    public void testCreateNewBook() {
        logSection(TEST_CLASS_NAME + " - Create New Book");

        logStep(1, "Creating new book object");
        Book newBook = Book.builder()
                .title("Automated Testing Guide")
                .description("A comprehensive guide to API automation testing")
                .pageCount(350)
                .excerpt("Learn the best practices for API testing")
                .publishDate("2024-02-08T00:00:00")
                .build();
        logInfo("New book: " + newBook.getTitle());

        logStep(2, "Sending POST request to create book");
        Response response = ApiRequestHelper.postRequest(ApiConfig.getBooksEndpointURL(), newBook);

        logStep(3, "Verifying HTTP 201 Created status code");
        AssertionHelper.assertStatusCode201(response, "Expected HTTP 201 Created status code");

        logStep(4, "Validating response contains created book");
        Book createdBook = response.as(Book.class);
        Assert.assertNotNull(createdBook, "Created book should not be null");
        Assert.assertEquals(createdBook.getTitle(), newBook.getTitle(), "Title should match");
        Assert.assertTrue(createdBook.getId() > 0, "Created book should have an ID");

        logSuccess("POST create book test passed");
    }

    @Test(priority = 4, description = "PUT update book - Happy Path")
    public void testUpdateBook() {
        logSection(TEST_CLASS_NAME + " - Update Book");

        logStep(1, "Fetching first book to get valid ID");
        Response allBooksResponse = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL());
        List<Book> books = allBooksResponse.jsonPath().getList("", Book.class);
        int bookId = books.get(0).getId();
        logInfo("Using book ID: " + bookId);

        logStep(2, "Creating updated book object");
        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Updated: API Testing Masterclass")
                .description("Updated description for comprehensive testing")
                .pageCount(400)
                .excerpt("Master API testing techniques")
                .publishDate("2024-02-08T00:00:00")
                .build();
        logInfo("Updated book title: " + updatedBook.getTitle());

        logStep(3, "Sending PUT request to update book");
        Response response = ApiRequestHelper.putRequest(
            ApiConfig.getBooksEndpointURL() + "/" + bookId,
            updatedBook
        );

        logStep(4, "Verifying HTTP 200 OK status code");
        AssertionHelper.assertStatusCode200(response, "Expected HTTP 200 OK status code");

        logSuccess("PUT update book test passed");
    }

    @Test(priority = 5, description = "DELETE book - Happy Path")
    public void testDeleteBook() {
        logSection(TEST_CLASS_NAME + " - Delete Book");

        logStep(1, "Fetching a book to delete");
        Response allBooksResponse = ApiRequestHelper.getRequest(ApiConfig.getBooksEndpointURL());
        List<Book> books = allBooksResponse.jsonPath().getList("", Book.class);
        int bookId = books.get(0).getId();
        logInfo("Deleting book ID: " + bookId);

        logStep(2, "Sending DELETE request");
        Response response = ApiRequestHelper.deleteRequest(ApiConfig.getBooksEndpointURL() + "/" + bookId);

        logStep(3, "Verifying HTTP 200 status code");
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 204,
            "Expected HTTP 200 or 204 status code, got " + statusCode);

        logSuccess("DELETE book test passed");
    }
}

