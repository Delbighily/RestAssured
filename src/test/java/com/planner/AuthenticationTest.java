package com.planner;

import data.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;
import specs.ApiSpecification;

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
    @Description("Verify that authentication succeeds with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
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
    @Description("Verify that authentication fails with incorrect tenant name")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Verify that authentication fails with incorrect username")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Verify that authentication fails with incorrect password")
    @Severity(SeverityLevel.NORMAL)
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
    @Description("Verify that authentication fails with empty tenant name")
    @Severity(SeverityLevel.MINOR)
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
