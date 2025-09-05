package com.solvd.api.users;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class GetUserByIdMethod extends RestApiMethod {

    public static final String GET_JSON_SCHEMA_PATH = "users/_get/byId/rs.json";

    public GetUserByIdMethod(Integer userId) {
        super(Constants.GOREST_BASE_URL + "/users/" + userId);
    }
}
