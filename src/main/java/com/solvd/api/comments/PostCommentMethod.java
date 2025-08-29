package com.solvd.api.comments;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class PostCommentMethod extends BaseRestAssuredMethod {
    public static final String POST_JSON_SCHEMA_PATH = "comments/_post/rs.json";

    public PostCommentMethod() {
        endpointUrl = Constants.GOREST_BASE_URL + "/comments";
    }
}
