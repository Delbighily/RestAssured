package com.planner;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthenticationTest {

    private RequestSpecification requestSpec;

    /**
     * Setup method to initialize shared RequestSpecification
     */
    @BeforeClass
    public void setup() {
        requestSpec = ApiSpecification.buildRequestSpecification();
    }

    /**
     * Test Case 1: Correct credentials should return successful response
     */
    @Test
    public void testAuthenticateWithCorrectCredentials() {
        TestData testData = new TestData();
        given()
            .spec(requestSpec)
            .body(testData.toJson())
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .assertThat()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("result.accessToken", notNullValue())
            .body("result.accessToken", not(emptyString()));
    }


    /**
     * Test Case 2: Wrong tenancy name should return authentication failure
     */
    @Test
    public void testAuthenticateWithWrongTenantName() {
        TestData testData = new TestData()
            .setTenantName("wrongtenant");
        given()
            .spec(requestSpec)
            .body(testData.toJson())
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 3: Wrong username should return authentication failure
     */
    @Test
    public void testAuthenticateWithWrongUsername() {
        TestData testData = new TestData()
            .setUserNameOrEmailAddress("wronguser");
        given()
            .spec(requestSpec)
            .body(testData.toJson())
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 4: Wrong password should return authentication failure
     */
    @Test
    public void testAuthenticateWithWrongPassword() {
        TestData testData = new TestData()
            .setPassword("wrongpassword");
        given()
            .spec(requestSpec)
            .body(testData.toJson())
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 5: Empty tenancy name should return authentication failure or bad request
     */
    @Test
    public void testAuthenticateWithEmptyTenantName() {
        TestData testData = new TestData()
            .setTenantName("");
        given()
            .spec(requestSpec)
            .body(testData.toJson())
        .when()
            .post()
        .then()
            .log().ifValidationFails()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }
}

