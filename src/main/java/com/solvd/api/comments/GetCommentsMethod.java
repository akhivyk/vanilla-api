package com.solvd.api.comments;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class GetCommentsMethod extends BaseRestAssuredMethod {
    public static final String GET_JSON_SCHEMA_PATH = "comments/_get/rs.json";

    public GetCommentsMethod() {
        endpointUrl = Constants.GOREST_BASE_URL + "/comments";
    }
}
