package com.solvd.utils;

import com.solvd.enums.HttpStatus;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestAssuredUtils {

    public static void assertStatusCode(Response response, HttpStatus httpStatus) {
        response.then().assertThat().statusCode(httpStatus.getStatusCode());
    }

    public static void assertContentType(Response response, ContentType contentType) {
        response.then().assertThat().contentType(contentType);
    }

    public static void assertSchema(Response response, String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    public static void validateResponse(Response response, HttpStatus expectedStatus, ContentType contentType, String schemaPath) {
        assertStatusCode(response, expectedStatus);
        if (contentType != null) {
            assertContentType(response, contentType);
        }
        if (schemaPath != null) {
            assertSchema(response, schemaPath);
        }
    }
}
