package com.solvd.api.comments;

import com.solvd.api.BaseRestAssuredMethod;
import com.solvd.utils.Constants;

public class DeleteCommentMethod extends BaseRestAssuredMethod {

    public DeleteCommentMethod(Integer commentId) {
        endpointUrl = Constants.GOREST_BASE_URL + "/comments/" + commentId;
    }
}
