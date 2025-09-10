package com.solvd.api.comments;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class DeleteCommentMethod extends RestApiMethod {

    public DeleteCommentMethod(Integer commentId) {
        super(Constants.GOREST_BASE_URL + "/comments/" + commentId);
    }
}
