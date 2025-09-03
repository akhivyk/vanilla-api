package com.solvd.api.comments;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class GetCommentByIdMethod extends RestApiMethod {
    public static final String GET_JSON_SCHEMA_PATH = "comments/_get/byId/rs.json";

    public GetCommentByIdMethod(Integer commentId) {
        super(Constants.GOREST_BASE_URL + "/comments/" + commentId);
    }
}
