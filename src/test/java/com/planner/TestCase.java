package com.planner;

import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
public class TestCase {


    @Test
    public void test(){
        given().baseUri("http://69.10.56.98:84/api/services/app/Project").auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWJkYWxsYWhUZXN0IiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoidGVzdEBtYWlsLmNvbSIsIkFzcE5ldC5JZGVudGl0eS5TZWN1cml0eVN0YW1wIjoiRjVYWVlNNjdQVEVOS0dQVDJGNkVNVkNBNkxTWU41TVoiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbiIsImh0dHA6Ly93d3cuYXNwbmV0Ym9pbGVycGxhdGUuY29tL2lkZW50aXR5L2NsYWltcy90ZW5hbnRJZCI6IjEiLCJzdWIiOiIyIiwianRpIjoiOWY2MjAzZWUtNGIxMi00OWIyLThiZmQtMWM4ZWRjZGU5Yzk0IiwiaWF0IjoxNzc0NDIzNDA5LCJuYmYiOjE3NzQ0MjM0MDksImV4cCI6MTc3NDUwOTgwOSwiaXNzIjoieDM2MFBsYW5uZXIiLCJhdWQiOiJ4MzYwUGxhbm5lciJ9.Qyx3oBdNSKPDoG5g6mjz9ZkDE4uo23TgxK9bQ8TOIQU")
                .when().get("GetUserProjectsStatistics")
                .then().log().all().assertThat().statusCode(200);
    }
}
