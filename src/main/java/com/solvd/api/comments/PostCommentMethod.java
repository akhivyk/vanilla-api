package com.solvd.api.comments;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class PostCommentMethod extends RestApiMethod {
    public static final String POST_JSON_SCHEMA_PATH = "comments/_post/rs.json";

    public PostCommentMethod() {
        super(Constants.GOREST_BASE_URL + "/comments");
    }
}
