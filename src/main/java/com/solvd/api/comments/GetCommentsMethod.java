package com.solvd.api.comments;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class GetCommentsMethod extends RestApiMethod {
    public static final String GET_JSON_SCHEMA_PATH = "comments/_get/rs.json";

    public GetCommentsMethod() {
        super(Constants.GOREST_BASE_URL + "/comments");
    }
}
