package com.solvd.api.comments;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class UpdateCommentMethod extends BaseRestAssuredMethod {
    public static final String PUT_JSON_SCHEME_PATH = "comments/_put/rs.json";

    public UpdateCommentMethod(Integer commentId) {
        endpointUrl = Constants.GOREST_BASE_URL + "/comments/" + commentId;
    }
}
