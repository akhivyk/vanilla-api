package com.solvd.graphql;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class GraphQLRequest extends BaseRestAssuredMethod {
    public GraphQLRequest() {
        endpointUrl = Constants.GOREST_GRAPHQL_URL;
    }
}
