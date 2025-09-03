package com.solvd.api;

import com.solvd.enums.HttpMethod;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiMethod extends BaseRestAssuredMethod {

    public RestApiMethod(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public Response execute(HttpMethod methodType, Object requestBody) {
        RequestSpecification spec = prepareRequest(methodType, requestBody);
        return sendRequest(spec, methodType);
    }

    public Response execute(HttpMethod methodType) {
        RequestSpecification spec = prepareRequest(methodType, null);
        return sendRequest(spec, methodType);
    }
}
