import com.solvd.api.comments.*;
import com.solvd.api.posts.GetPostsMethod;
import com.solvd.api.users.GetUsersMethod;
import com.solvd.api.users.PostUserMethod;
import com.solvd.api.users.UpdateUserMethod;
import com.solvd.entity.Comment;
import com.solvd.entity.User;
import com.solvd.enums.HttpMethod;
import com.solvd.enums.HttpStatus;
import com.solvd.utils.JsonUtils;
import com.solvd.utils.RestAssuredUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Random;

@Slf4j
public class GorestTest {
    private Comment createdComment;
    private User createdUser;
    private List<Comment> existingComments;

    @DataProvider(name = "type-update-requests")
    public static Object[][] getTypeUpdateRequests() {
        return new Object[][]{
                {HttpMethod.PUT},
                {HttpMethod.PATCH}
        };
    }

    @Test
    public void getAllComments() {
        GetCommentsMethod getCommentsMethod = new GetCommentsMethod();

        Response response = getCommentsMethod.execute(HttpMethod.GET);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, GetCommentsMethod.GET_JSON_SCHEMA_PATH);

        existingComments = JsonUtils.toList(response, Comment.class);
    }

    @Test(dependsOnMethods = "getAllComments")
    public void postCreateComment() {
        SoftAssert softAssert = new SoftAssert();

        PostCommentMethod commentMethod = new PostCommentMethod();

        Comment expectedComment = Comment.generateComment(
                existingComments.get(new Random().nextInt(existingComments.size())).getPost_id()
        );
        Response response = commentMethod.execute(HttpMethod.POST, expectedComment);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.CREATED);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, PostCommentMethod.POST_JSON_SCHEMA_PATH);

        createdComment = JsonUtils.toObject(response, Comment.class);

        softAssert.assertEquals(createdComment.getPost_id(), expectedComment.getPost_id(),
                "Post id of created comment isn't equals to expected");
        softAssert.assertEquals(createdComment.getBody(), expectedComment.getBody(),
                "Body of created comment isn't equals to expected");

        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "postCreateComment")
    public void getCommentById() {
        GetCommentByIdMethod getCommentByIdMethod = new GetCommentByIdMethod(createdComment.getId());

        Response response = getCommentByIdMethod.execute(HttpMethod.GET);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, GetCommentByIdMethod.GET_JSON_SCHEMA_PATH);

        Comment retrievedComment = JsonUtils.toObject(response, Comment.class);

        Assert.assertEquals(retrievedComment.getId(), createdComment.getId(),
                "Id of retrieved comment isn't equals to expected!");
    }

    @Test(dependsOnMethods = "postCreateComment", dataProvider = "type-update-requests")
    public void updateComment(HttpMethod updateRequestType) {
        log.info("Current request type - " + updateRequestType.name());

        UpdateCommentMethod updateCommentMethod = new UpdateCommentMethod(createdComment.getId());

        String updatedCommentText = "updComment: " + RandomStringUtils.randomAlphabetic(8);
        createdComment.setBody(updatedCommentText);

        Response response = updateCommentMethod.execute(updateRequestType, createdComment);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, UpdateCommentMethod.PUT_JSON_SCHEME_PATH);

        Comment updatedComment = JsonUtils.toObject(response, Comment.class);

        Assert.assertEquals(updatedComment.getId(), createdComment.getId(),
                "Id of updated comment isn't equals to expected!");
        Assert.assertEquals(updatedComment.getBody(), updatedCommentText,
                "Comment body isn't equals to expected after updating");
    }

    @Test(dependsOnMethods = "updateComment")
    public void deleteComment() {
        DeleteCommentMethod deleteCommentMethod = new DeleteCommentMethod(createdComment.getId());

        Response response = deleteCommentMethod.execute(HttpMethod.DELETE);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.NO_CONTENT);

        GetCommentByIdMethod getCommentByIdMethod = new GetCommentByIdMethod(createdComment.getId());

        response = getCommentByIdMethod.execute(HttpMethod.GET);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.NOT_FOUND);
    }

    @Test
    public void postCreateUser() {
        SoftAssert softAssert = new SoftAssert();

        PostUserMethod userMethod = new PostUserMethod();

        User expectedUser = User.generateUser();
        Response response = userMethod.execute(HttpMethod.POST, expectedUser);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.CREATED);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, PostUserMethod.POST_JSON_SCHEMA_PATH);

        createdUser = JsonUtils.toObject(response, User.class);

        softAssert.assertEquals(createdUser.getName(), expectedUser.getName(),
                "Name of created user isn't equals to expected");
        softAssert.assertEquals(createdUser.getEmail(), expectedUser.getEmail(),
                "Email of created user isn't equals to expected");

        softAssert.assertAll();
    }

    @Test
    public void getAllUsers() {
        GetUsersMethod getUsersMethod = new GetUsersMethod();

        Response response = getUsersMethod.execute(HttpMethod.GET);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, GetUsersMethod.GET_JSON_SCHEMA_PATH);
    }

    @Test(dependsOnMethods = "postCreateUser", dataProvider = "type-update-requests")
    public void updateUser(HttpMethod updateRequestType) {
        log.info("Current request type - " + updateRequestType.name());

        UpdateUserMethod updateUserMethod = new UpdateUserMethod(createdUser.getId());

        String updatedUserName = "UpdatedName" + RandomStringUtils.randomAlphabetic(3);
        createdUser.setName(updatedUserName);

        Response response = updateUserMethod.execute(updateRequestType, createdUser);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, UpdateUserMethod.UPDATE_JSON_SCHEMA_PATH);

        User updatedUser = JsonUtils.toObject(response, User.class);

        Assert.assertEquals(updatedUser.getId(), createdUser.getId(),
                "Id of updated user isn't equals to expected!");
        Assert.assertEquals(updatedUser.getName(), updatedUserName,
                "User name isn't equals to expected after updating");
    }

    @Test
    public void getAllPosts() {
        GetPostsMethod getPostsMethod = new GetPostsMethod();

        Response response = getPostsMethod.execute(HttpMethod.GET);

        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        RestAssuredUtils.assertContentType(response, ContentType.JSON);
        RestAssuredUtils.assertSchema(response, GetPostsMethod.GET_JSON_SCHEMA_PATH);
    }
}
