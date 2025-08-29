package com.solvd.api.posts;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class GetPostsMethod extends BaseRestAssuredMethod {
    public static final String GET_JSON_SCHEMA_PATH = "posts/rs.json";

    public GetPostsMethod() {
        endpointUrl = Constants.GOREST_BASE_URL + "/posts";
    }
}
