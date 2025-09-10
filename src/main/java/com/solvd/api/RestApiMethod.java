package com.solvd.api;

import com.solvd.enums.HttpMethod;
import com.solvd.factory.ObjectMapperFactory;
import com.solvd.utils.ConfigReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiMethod extends AbstractRestClient {

    public RestApiMethod(String endpointUrl) {
        super(ConfigReader.getConfigValue("token"), ObjectMapperFactory.buildNew());
        this.endpointUrl = endpointUrl;
    }

    public Response execute(HttpMethod methodType, Object requestBody) {
        RequestSpecification spec = prepareRequest(requestBody);
        return sendRequest(spec, methodType);
    }

    public Response execute(HttpMethod methodType) {
        RequestSpecification spec = prepareRequest(null);
        return sendRequest(spec, methodType);
    }
}
