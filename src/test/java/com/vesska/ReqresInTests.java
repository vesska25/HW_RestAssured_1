package com.vesska;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;

public class ReqresInTests {

    @Test
    void createUserTest() {

        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .when()
                .body(data)
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .statusCode(201)
                .body("job", is("leader"))
                .body("name", is("morpheus"));
    }

    @Test
    void getSingleRequestTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().status()
                .body("data.id", is(2))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void updateUserJobTest() {

        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .when()
                .body(data)
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void notFoundTest() {

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void unsuccessfulLoginTest() {

        String data = "{ \"email\": \"peter@klaven\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}