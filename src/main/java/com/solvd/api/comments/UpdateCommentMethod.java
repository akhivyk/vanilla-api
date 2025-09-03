package com.solvd.api.comments;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class UpdateCommentMethod extends RestApiMethod {
    public static final String PUT_JSON_SCHEME_PATH = "comments/_put/rs.json";

    public UpdateCommentMethod(Integer commentId) {
        super(Constants.GOREST_BASE_URL + "/comments/" + commentId);
    }
}
