package com.solvd.api.users;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class GetUsersMethod extends BaseRestAssuredMethod {
    public static final String GET_JSON_SCHEMA_PATH = "users/_get/rs.json";

    public GetUsersMethod() {
        endpointUrl = Constants.GOREST_BASE_URL + "/users";
    }
}
