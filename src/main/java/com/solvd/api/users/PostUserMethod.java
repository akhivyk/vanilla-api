package com.solvd.api.users;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class PostUserMethod extends BaseRestAssuredMethod {

    public static final String POST_JSON_SCHEMA_PATH = "users/_post/rs.json";

    public PostUserMethod() {
        endpointUrl = Constants.GOREST_BASE_URL + "/users";
    }
}
