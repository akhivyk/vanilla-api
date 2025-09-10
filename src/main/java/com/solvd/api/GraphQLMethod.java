package com.solvd.api;

import com.solvd.enums.HttpMethod;
import com.solvd.factory.ObjectMapperFactory;
import com.solvd.graphql.GraphQlQuery;
import com.solvd.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GraphQLMethod extends AbstractRestClient {

    public GraphQLMethod(String endpointUrl) {
        super(ConfigReader.getConfigValue("token"), ObjectMapperFactory.buildNew());
        this.endpointUrl = endpointUrl;
    }

    public Response executeGraphQL(HttpMethod methodType, GraphQlQuery query) {
        RequestSpecification spec = RestAssured.given()
                .header("Authorization", "Bearer " + this.bearerToken)
                .contentType(ContentType.JSON)
                .log().all()
                .body(query);

        return sendRequest(spec, methodType);
    }
}

