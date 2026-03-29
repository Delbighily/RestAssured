package com.planner;

import io.restassured.http.ContentType;
import org.testng.annotations.*;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthenticationTest {

    private static final String BASE_URI = "http://69.10.56.98:84/api/TokenAuth/Authenticate";

    /**
     * Test Case 1: Correct credentials should return successful response
     */
    @Test
    public void testAuthenticateWithCorrectCredentials() {
        File datastorage = new File("src/test/resources/data.json");
        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body(datastorage)
        .when()
            .post()
        .then()
            .log().all()
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
        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body("{\"userNameOrEmailAddress\":\"abdallahtest\",\"password\":\"Aa@123456\",\"rememberClient\":false,\"tenantName\":\"wrongtenant\"}")
        .when()
            .post()
        .then()
            .log().all()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 3: Wrong username should return authentication failure
     */
    @Test
    public void testAuthenticateWithWrongUsername() {
        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body("{\"userNameOrEmailAddress\":\"wronguser\",\"password\":\"Aa@123456\",\"rememberClient\":false,\"tenantName\":\"abdallahtest\"}")
        .when()
            .post()
        .then()
            .log().all()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 4: Wrong password should return authentication failure
     */
    @Test
    public void testAuthenticateWithWrongPassword() {
        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body("{\"userNameOrEmailAddress\":\"abdallahtest\",\"password\":\"wrongpassword\",\"rememberClient\":false,\"tenantName\":\"abdallahtest\"}")
        .when()
            .post()
        .then()
            .log().all()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }

    /**
     * Test Case 5: Empty tenancy name should return authentication failure or bad request
     */
    @Test
    public void testAuthenticateWithEmptyTenantName() {
        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body("{\"userNameOrEmailAddress\":\"abdallahtest\",\"password\":\"Aa@123456\",\"rememberClient\":false,\"tenantName\":\"\"}")
        .when()
            .post()
        .then()
            .log().all()
            .assertThat()
            .statusCode(500)
            .body("error.message", containsString("User Not Found"));
    }
}

