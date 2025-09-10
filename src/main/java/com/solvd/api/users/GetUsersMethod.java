package com.solvd.api.users;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class GetUsersMethod extends RestApiMethod {
    public static final String GET_JSON_SCHEMA_PATH = "users/_get/rs.json";

    public GetUsersMethod() {
        super(Constants.GOREST_BASE_URL + "/users");
    }
}
