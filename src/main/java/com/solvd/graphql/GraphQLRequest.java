package com.solvd.graphql;

import com.solvd.api.GraphQLMethod;
import com.solvd.utils.Constants;

public class GraphQLRequest extends GraphQLMethod {
    public GraphQLRequest() {
        super(Constants.GOREST_GRAPHQL_URL);
    }
}
