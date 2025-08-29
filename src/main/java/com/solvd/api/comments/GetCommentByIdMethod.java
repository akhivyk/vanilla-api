package com.solvd.api.comments;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class GetCommentByIdMethod extends BaseRestAssuredMethod {
    public static final String GET_JSON_SCHEMA_PATH = "comments/_get/byId/rs.json";

    public GetCommentByIdMethod(Integer commentId) {
        endpointUrl = Constants.GOREST_BASE_URL + "/comments/" + commentId;
    }
}
