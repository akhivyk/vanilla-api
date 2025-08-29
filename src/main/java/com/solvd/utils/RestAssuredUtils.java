package com.solvd.utils;

import com.solvd.enums.StatusCodeEnum;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestAssuredUtils {

    public static void assertStatusCode(Response response, StatusCodeEnum statusCode) {
        response.then().assertThat().statusCode(statusCode.getStatusCode());
    }

    public static void assertContentType(Response response, ContentType contentType) {
        response.then().assertThat().contentType(contentType);
    }

    public static void assertSchema(Response response, String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}
