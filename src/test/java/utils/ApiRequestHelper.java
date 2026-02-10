package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiRequestHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestHelper.class);

    public static RequestSpecification getRequestSpec() {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public static Response getRequest(String url) {
        logger.info("Making GET request to: {}", url);
        return getRequestSpec()
                .when()
                .get(url);
    }

    public static Response getRequestWithParam(String url, String pathParam, String paramValue) {
        logger.info("Making GET request to: {} with param {}={}", url, pathParam, paramValue);
        return getRequestSpec()
                .pathParam(pathParam, paramValue)
                .when()
                .get(url);
    }

    public static Response postRequest(String url, Object body) {
        logger.info("Making POST request to: {} with body", url);
        return getRequestSpec()
                .body(body)
                .when()
                .post(url);
    }

    public static Response putRequest(String url, Object body) {
        logger.info("Making PUT request to: {} with body", url);
        return getRequestSpec()
                .body(body)
                .when()
                .put(url);
    }

    public static Response deleteRequest(String url) {
        logger.info("Making DELETE request to: {}", url);
        return getRequestSpec()
                .when()
                .delete(url);
    }

    public static Response patchRequest(String url, Object body) {
        logger.info("Making PATCH request to: {} with body", url);
        return getRequestSpec()
                .body(body)
                .when()
                .patch(url);
    }

    public static void logResponse(Response response) {
        logger.info("Response Status Code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody().asString());
    }

    public static void prettyPrintResponse(Response response) {
        response.prettyPrint();
    }
}

