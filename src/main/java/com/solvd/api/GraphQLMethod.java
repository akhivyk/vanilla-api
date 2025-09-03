package com.solvd.api;

import com.solvd.enums.HttpMethod;
import com.solvd.graphql.GraphQlQuery;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GraphQLMethod extends BaseRestAssuredMethod {

    public GraphQLMethod(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public Response executeGraphQL(HttpMethod methodType, GraphQlQuery query) {
        RequestSpecification spec = RestAssured.given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .contentType(ContentType.JSON)
                .log().all()
                .body(query);

        return sendRequest(spec, methodType);
    }
}

