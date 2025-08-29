package com.solvd.api.users;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class UpdateUserMethod extends BaseRestAssuredMethod {
    public static final String UPDATE_JSON_SCHEMA_PATH = "users/_put/rs.json";

    public UpdateUserMethod(Integer userId) {
        endpointUrl = Constants.GOREST_BASE_URL + "/users/" + userId;
    }
}
