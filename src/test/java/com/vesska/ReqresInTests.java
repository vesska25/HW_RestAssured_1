package com.vesska;

import com.vesska.models.CreateUser;
import com.vesska.models.UserData;
import org.junit.jupiter.api.Test;

import static com.vesska.Specs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInTests {

    @Test
    void createUserTest() {

        CreateUser data = new CreateUser();
        data.setName("morpheus");
        data.setJob("leader");

        given()
                .spec(request)
                .when()
                .body(data)
                .post("/users")
                .then()
                .spec(response201)
                .extract().as(UserData.class);
        assertEquals("morpheus", data.getName());
        assertEquals("leader", data.getJob());
    }

    @Test
    void getSingleRequestTest() {
        UserData data = given()
                .spec(request)
                .when()
                .get("/unknown/2")
                .then()
                .spec(response200)
                .extract().as(UserData.class);
        assertEquals(2,data.getUser().getId());
    }

    @Test
    void updateUserJobTest() {

        CreateUser data = new CreateUser();
        data.setName("morpheus");
        data.setJob("zion resident");

        given()
                .spec(request)
                .when()
                .body(data)
                .put("/users/2")
                .then()
                .spec(response200)
                .extract().as(UserData.class);
        assertEquals("morpheus", data.getName());
        assertEquals("zion resident", data.getJob());
    }

    @Test
    void notFoundTest() {

        given()
                .spec(request)
                .when()
                .get("/unknown/23")
                .then()
                .spec(response404);
    }

    @Test
    void unsuccessfulLoginTest() {

        CreateUser data = new CreateUser();
        data.setName("peter@klaven");

        given()
                .spec(request)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(response400)
                .log().body()
                .body("error", is("Missing email or username"))
                .extract().as(UserData.class);
    }

    @Test
    void listOfUsersTest(){
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response200)
                .log().body()
                .body("data.findAll{it.id == 11}.email", hasItem("george.edwards@reqres.in"));
    }
}