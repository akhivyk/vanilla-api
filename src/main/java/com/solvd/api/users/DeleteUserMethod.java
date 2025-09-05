package com.solvd.api.users;

import com.solvd.api.RestApiMethod;
import com.solvd.utils.Constants;

public class DeleteUserMethod extends RestApiMethod {

    public DeleteUserMethod(Integer userId) {
        super(Constants.GOREST_BASE_URL + "/users/" + userId);
    }
}
