package com.solvd.api.users;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class PostUserMethod extends RestApiMethod {

    public static final String POST_JSON_SCHEMA_PATH = "users/_post/rs.json";

    public PostUserMethod() {
        super(Constants.GOREST_BASE_URL + "/users");
    }
}
