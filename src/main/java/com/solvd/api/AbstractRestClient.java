package com.solvd.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.enums.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractRestClient {
    protected String bearerToken;
    protected ObjectMapper objectMapper;
    protected String endpointUrl;

    protected AbstractRestClient(String bearerToken, ObjectMapper objectMapper) {
        this.bearerToken = bearerToken;
        this.objectMapper = objectMapper;
    }

    protected RequestSpecification prepareRequest(Object requestBody) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .log().all();

        if (requestBody != null) {
            try {
                String jsonBody = objectMapper.writeValueAsString(requestBody);
                requestSpec.contentType(ContentType.JSON).body(jsonBody);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Failed to serialize request body to JSON", e);
            }
        }

        return requestSpec;
    }

    protected Response sendRequest(RequestSpecification spec, HttpMethod methodType) {
        Response response = spec.request(methodType.name(), endpointUrl);
        response.then().log().all();
        return response;
    }
}
