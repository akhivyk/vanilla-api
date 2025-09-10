package com.solvd.api.posts;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class GetPostsMethod extends RestApiMethod {
    public static final String GET_JSON_SCHEMA_PATH = "posts/rs.json";

    public GetPostsMethod() {
        super(Constants.GOREST_BASE_URL + "/posts");
    }
}
