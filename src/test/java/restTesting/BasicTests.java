package restTesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

public class BasicTests {

     private final String BASE_URL = "https://api.github.com";

    @Test
    public void withParam(){
        RestAssured
                .given()
                .pathParam("user", "IvayloVasiliev")
                .pathParam("repo", "API-Tests")
                .get("https://github.com/{user}/{repo}")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

     @Test
     public void overloadedGet(){
         RestAssured
                 .get("https://github.com/{user}/{repo}", "IvayloVasiliev", "API-Tests")
                 .prettyPeek()
                 .then()
                 .statusCode(200);
     }

    @Test
    public void simpleHamcrestMatchers(){
       RestAssured.get(BASE_URL)
               .then()
               .statusCode(200)
               .statusCode(lessThan(300))
               .header("cache-control", containsStringIgnoringCase("max-age=60"))
               .time(lessThan(2L), TimeUnit.SECONDS)
               .header("etag", notNullValue());
    }

    @Test
    public void genericHeader(){
        Response responce = RestAssured.get(BASE_URL);
        Assert.assertEquals(responce.getHeader("server"), "GitHub.com");
        Assert.assertEquals(responce.getHeader("x-ratelimit-limit"), "60");
    }

    @Test
    public void convenienceMethods(){
        Response responce = RestAssured.get(BASE_URL);
        Assert.assertEquals(responce.getStatusCode(), 200);
        Assert.assertEquals(responce.getContentType(), "application/json; charset=utf-8");
    }

    //shows only the response body as a string
    @Test
    public void print(){
        RestAssured.get(BASE_URL)
                .print();
    }

    //shows only the response body as a string arranged JSON
    @Test
    public void prettyPrint(){
        RestAssured.get(BASE_URL)
                .prettyPrint();
    }

    //shows response header and body
     @Test
     public void peek(){
         RestAssured.get(BASE_URL)
                 .peek();
     }

     //shows header and arranged JSON body
    @Test
    public void prettyPeek(){
        RestAssured.get(BASE_URL)
                .prettyPeek();
    }

    //checking the status code of the page
    @Test
    public void headerStutusCheck(){
        RestAssured.get(BASE_URL)
                .then()
                .statusCode(200);
    }
}
