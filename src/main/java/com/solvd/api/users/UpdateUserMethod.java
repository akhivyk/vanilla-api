package com.solvd.api.users;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class UpdateUserMethod extends RestApiMethod {
    public static final String UPDATE_JSON_SCHEMA_PATH = "users/_put/rs.json";

    public UpdateUserMethod(Integer userId) {
        super(Constants.GOREST_BASE_URL + "/users/" + userId);
    }
}
