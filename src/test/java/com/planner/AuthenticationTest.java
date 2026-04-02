package com.planner;

import data.TestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;
import specs.ApiSpecification;
import com.planner.utils.ResponseValidator;
import com.planner.utils.ApiTestHelper;

import static org.hamcrest.Matchers.*;

@Epic("Planner API")
@Feature("Authentication")
public class AuthenticationTest {

    private RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        requestSpec = ApiSpecification.buildRequestSpecification();
    }

    @Test
    @Story("Login with valid credentials")
    @Description("Verify that authentication succeeds with correct credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void authenticate_with_correct_credentials_should_succeed() {
        TestData testData = new TestData();
        Response response = ApiTestHelper.postRequest(requestSpec, testData.toJson());

        ResponseValidator.validateResponse(response, responseSpec ->
                responseSpec.assertThat()
                        .statusCode(200)
                        .body("success", equalTo(true))
                        .body("result.accessToken", notNullValue())
                        .body("result.accessToken", not(emptyString()))
        );
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Verify that authentication fails with incorrect tenant name")
    @Severity(SeverityLevel.NORMAL)
    public void authenticate_with_wrong_tenant_name_should_fail() {
        TestData testData = new TestData()
                .setTenantName("wrongtenant");
        Response response = ApiTestHelper.postRequest(requestSpec, testData.toJson());

        ResponseValidator.validateResponse(response, responseSpec ->
                responseSpec.assertThat()
                        .statusCode(500)
                        .body("error.message", containsString("User Not Found"))
        );
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Verify that authentication fails with incorrect username")
    @Severity(SeverityLevel.NORMAL)
    public void authenticate_with_wrong_username_should_fail() {
        TestData testData = new TestData()
                .setUserNameOrEmailAddress("wronguser");
        Response response = ApiTestHelper.postRequest(requestSpec, testData.toJson());

        ResponseValidator.validateResponse(response, responseSpec ->
                responseSpec.assertThat()
                        .statusCode(500)
                        .body("error.message", containsString("User Not Found"))
        );
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Verify that authentication fails with incorrect password")
    @Severity(SeverityLevel.NORMAL)
    public void authenticate_with_wrong_password_should_fail() {
        TestData testData = new TestData()
                .setPassword("wrongpassword");
        Response response = ApiTestHelper.postRequest(requestSpec, testData.toJson());

        ResponseValidator.validateResponse(response, responseSpec ->
                responseSpec.assertThat()
                        .statusCode(500)
                        .body("error.message", containsString("User Not Found"))
        );
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Verify that authentication fails with empty tenant name")
    @Severity(SeverityLevel.MINOR)
    public void authenticate_with_empty_tenant_name_should_fail() {
        TestData testData = new TestData()
                .setTenantName("");
        Response response = ApiTestHelper.postRequest(requestSpec, testData.toJson());

        ResponseValidator.validateResponse(response, responseSpec ->
                responseSpec.assertThat()
                        .statusCode(500)
                        .body("error.message", containsString("User Not Found"))
        );
    }
}
