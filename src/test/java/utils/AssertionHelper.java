package utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class AssertionHelper {

    private static final Logger logger = LoggerFactory.getLogger(AssertionHelper.class);

    public static void assertStatusCode200(Response response, String message) {
        Assert.assertEquals(response.getStatusCode(), 200, message);
        logger.info("✓ Status code 200 assertion passed");
    }

    public static void assertStatusCode201(Response response, String message) {
        Assert.assertEquals(response.getStatusCode(), 201, message);
        logger.info("✓ Status code 201 assertion passed");
    }

    public static void assertStatusCode204(Response response, String message) {
        Assert.assertEquals(response.getStatusCode(), 204, message);
        logger.info("✓ Status code 204 assertion passed");
    }

    public static void assertStatusCode404(Response response, String message) {
        Assert.assertEquals(response.getStatusCode(), 404, message);
        logger.info("✓ Status code 404 assertion passed");
    }

    public static void assertStatusCode(Response response, int expectedStatusCode, String message) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, message);
        logger.info("✓ Status code {} assertion passed", expectedStatusCode);
    }

    public static void assertResponseContains(Response response, String text, String message) {
        Assert.assertTrue(response.getBody().asString().contains(text), message);
        logger.info("✓ Response contains '{}' assertion passed", text);
    }

    public static void assertResponseNotContains(Response response, String text, String message) {
        Assert.assertFalse(response.getBody().asString().contains(text), message);
        logger.info("✓ Response does not contain '{}' assertion passed", text);
    }

    public static void assertHeaderPresent(Response response, String headerName, String message) {
        Assert.assertNotNull(response.getHeader(headerName), message);
        logger.info("✓ Header '{}' is present", headerName);
    }

    public static void assertHeaderValue(Response response, String headerName, String expectedValue, String message) {
        Assert.assertEquals(response.getHeader(headerName), expectedValue, message);
        logger.info("✓ Header '{}' has value '{}'", headerName, expectedValue);
    }

    public static void assertResponseTime(Response response, long maxTimeMs, String message) {
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < maxTimeMs,
            message + " - Expected < " + maxTimeMs + "ms but got " + responseTime + "ms");
        logger.info("✓ Response time {} ms is within limit of {} ms", responseTime, maxTimeMs);
    }
}

