package com.solvd.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.enums.HttpMethod;
import com.solvd.factory.ObjectMapperFactory;
import com.solvd.utils.FileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRestAssuredMethod {
    protected static final String BEARER_TOKEN = FileReader.getConfigValue("token");
    protected static final ObjectMapper objectMapper = ObjectMapperFactory.buildNew();
    protected String endpointUrl;

    protected RequestSpecification prepareRequest(HttpMethod methodType, Object requestBody) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .log().all();

        if (requestBody != null &&
                (methodType == HttpMethod.POST || methodType == HttpMethod.PUT || methodType == HttpMethod.PATCH)) {
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
        Response response = switch (methodType) {
            case GET -> spec.get(endpointUrl);
            case POST -> spec.post(endpointUrl);
            case PUT -> spec.put(endpointUrl);
            case PATCH -> spec.patch(endpointUrl);
            case DELETE -> spec.delete(endpointUrl);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + methodType);
        };

        response.then().log().all();
        return response;
    }
}
