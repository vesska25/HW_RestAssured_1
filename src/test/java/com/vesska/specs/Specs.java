package com.vesska.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.vesska.helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification request = with()
            .log().uri()
            .log().headers()
            .log().body()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .basePath("/api")
            .contentType(ContentType.JSON);

    public static ResponseSpecification response200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
    public static ResponseSpecification response201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();
    public static ResponseSpecification response404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();
    public static ResponseSpecification response400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();
}
