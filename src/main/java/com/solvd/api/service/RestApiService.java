package com.solvd.api.service;

import com.solvd.api.comments.*;
import com.solvd.api.users.*;
import com.solvd.entity.Comment;
import com.solvd.entity.User;
import com.solvd.enums.HttpMethod;
import com.solvd.enums.HttpStatus;
import com.solvd.utils.JsonUtils;
import com.solvd.utils.RestAssuredUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

public class RestApiService {

    public User createUser(User expectedUser) {
        PostUserMethod userMethod = new PostUserMethod();
        Response response = userMethod.execute(HttpMethod.POST, expectedUser);

        RestAssuredUtils.validateResponse(response, HttpStatus.CREATED, ContentType.JSON, PostUserMethod.POST_JSON_SCHEMA_PATH);
        return JsonUtils.toObject(response, User.class);
    }

    public User updateUser(Integer id, User user, HttpMethod type) {
        UpdateUserMethod updateUserMethod = new UpdateUserMethod(id);
        Response response = updateUserMethod.execute(type, user);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, UpdateUserMethod.UPDATE_JSON_SCHEMA_PATH);
        return JsonUtils.toObject(response, User.class);
    }

    public void deleteUser(Integer id) {
        DeleteUserMethod deleteUserMethod = new DeleteUserMethod(id);
        Response response = deleteUserMethod.execute(HttpMethod.DELETE);

        RestAssuredUtils.validateResponse(response, HttpStatus.NO_CONTENT, null, null);
    }

    public List<User> getAllUsers() {
        GetUsersMethod getUsersMethod = new GetUsersMethod();
        Response response = getUsersMethod.execute(HttpMethod.GET);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, GetUsersMethod.GET_JSON_SCHEMA_PATH);
        return JsonUtils.toList(response, User.class);
    }

    public User getUserById(Integer id) {
        GetUserByIdMethod getUserByIdMethod = new GetUserByIdMethod(id);
        Response response = getUserByIdMethod.execute(HttpMethod.GET);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, GetUserByIdMethod.GET_JSON_SCHEMA_PATH);
        return JsonUtils.toObject(response, User.class);
    }

    public Comment createComment(Comment comment) {
        PostCommentMethod postCommentMethod = new PostCommentMethod();
        Response response = postCommentMethod.execute(HttpMethod.POST, comment);

        RestAssuredUtils.validateResponse(response, HttpStatus.CREATED, ContentType.JSON, PostCommentMethod.POST_JSON_SCHEMA_PATH);
        return JsonUtils.toObject(response, Comment.class);
    }

    public Comment updateComment(Integer id, Comment comment, HttpMethod type) {
        UpdateCommentMethod updateCommentMethod = new UpdateCommentMethod(id);
        Response response = updateCommentMethod.execute(type, comment);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, UpdateCommentMethod.PUT_JSON_SCHEME_PATH);
        return JsonUtils.toObject(response, Comment.class);
    }

    public void deleteComment(Integer id) {
        DeleteCommentMethod deleteCommentMethod = new DeleteCommentMethod(id);
        Response response = deleteCommentMethod.execute(HttpMethod.DELETE);

        RestAssuredUtils.validateResponse(response, HttpStatus.NO_CONTENT, null, null);
    }

    public List<Comment> getAllComments() {
        GetCommentsMethod getCommentsMethod = new GetCommentsMethod();
        Response response = getCommentsMethod.execute(HttpMethod.GET);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, GetCommentsMethod.GET_JSON_SCHEMA_PATH);
        return JsonUtils.toList(response, Comment.class);
    }

    public Comment getCommentById(Integer id) {
        GetCommentByIdMethod getCommentByIdMethod = new GetCommentByIdMethod(id);
        Response response = getCommentByIdMethod.execute(HttpMethod.GET);

        RestAssuredUtils.validateResponse(response, HttpStatus.OK, ContentType.JSON, GetCommentByIdMethod.GET_JSON_SCHEMA_PATH);
        return JsonUtils.toObject(response, Comment.class);
    }
}
