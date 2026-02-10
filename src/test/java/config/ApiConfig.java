package config;

public class ApiConfig {

    // Base URL for FakeRestAPI
    public static final String BASE_URL = "https://fakerestapi.azurewebsites.net";

    // API Versions
    public static final String API_V1 = "/api/v1";

    // Endpoints
    public static final String BOOKS_ENDPOINT = API_V1 + "/Books";
    public static final String AUTHORS_ENDPOINT = API_V1 + "/Authors";

    // HTTP Response codes
    public static final int HTTP_200_OK = 200;
    public static final int HTTP_201_CREATED = 201;
    public static final int HTTP_204_NO_CONTENT = 204;
    public static final int HTTP_400_BAD_REQUEST = 400;
    public static final int HTTP_404_NOT_FOUND = 404;
    public static final int HTTP_500_INTERNAL_SERVER_ERROR = 500;

    // Timeouts (in milliseconds)
    public static final long REQUEST_TIMEOUT = 5000;
    public static final long CONNECTION_TIMEOUT = 5000;

    // Content Type
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static String getEndpointURL(String endpoint) {
        return BASE_URL + endpoint;
    }

    public static String getBooksEndpointURL() {
        return getEndpointURL(BOOKS_ENDPOINT);
    }

    public static String getAuthorsEndpointURL() {
        return getEndpointURL(AUTHORS_ENDPOINT);
    }
}

