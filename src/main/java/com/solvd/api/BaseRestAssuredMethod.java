package com.solvd.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.enums.HttpMethodTypeEnum;
import com.solvd.factory.ObjectMapperFactory;
import com.solvd.graphql.GraphQlQuery;
import com.solvd.utils.FileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRestAssuredMethod {
    private static final String BEARER_TOKEN = FileReader.getConfigValue("token");
    private static final ObjectMapper objectMapper = ObjectMapperFactory.buildNew();
    public String endpointUrl;

    public Response execute(HttpMethodTypeEnum methodType, Object requestBody) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .log().all();

        if (methodType == HttpMethodTypeEnum.POST || methodType == HttpMethodTypeEnum.PUT || methodType == HttpMethodTypeEnum.PATCH) {
            String jsonBody;
            try {
                jsonBody = objectMapper.writeValueAsString(requestBody);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Failed to serialize request body to JSON", e);
            }
            requestSpec.contentType(ContentType.JSON)
                    .body(jsonBody);
        }

        return switch (methodType) {
            case GET -> requestSpec.get(endpointUrl);
            case POST -> requestSpec.post(endpointUrl);
            case PUT -> requestSpec.put(endpointUrl);
            case PATCH -> requestSpec.patch(endpointUrl);
            case DELETE -> requestSpec.delete(endpointUrl);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + methodType);
        };
    }

    public Response execute(HttpMethodTypeEnum methodType) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .log().all();

        if (methodType == HttpMethodTypeEnum.POST || methodType == HttpMethodTypeEnum.PUT || methodType == HttpMethodTypeEnum.PATCH) {
            requestSpec.contentType(ContentType.JSON);
        }

        return switch (methodType) {
            case GET -> requestSpec.get(endpointUrl);
            case POST -> requestSpec.post(endpointUrl);
            case PUT -> requestSpec.put(endpointUrl);
            case PATCH -> requestSpec.patch(endpointUrl);
            case DELETE -> requestSpec.delete(endpointUrl);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + methodType);
        };
    }

    public Response executeGraphQL(HttpMethodTypeEnum methodType, GraphQlQuery query) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .header("Content-Type", "application/json")
                .log().all();

        requestSpec.body(query);

        Response response = switch (methodType) {
            case GET -> requestSpec.get(endpointUrl);
            case POST -> requestSpec.post(endpointUrl);
            case PUT -> requestSpec.put(endpointUrl);
            case PATCH -> requestSpec.patch(endpointUrl);
            case DELETE -> requestSpec.delete(endpointUrl);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + methodType);
        };

        response.then().log().all();

        return response;
    }
}
