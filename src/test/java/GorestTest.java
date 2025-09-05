import com.solvd.api.comments.*;
import com.solvd.api.service.RestApiService;
import com.solvd.api.users.*;
import com.solvd.entity.Comment;
import com.solvd.entity.User;
import com.solvd.entity.utils.UserAssertions;
import com.solvd.enums.HttpMethod;
import com.solvd.enums.HttpStatus;
import com.solvd.utils.RestAssuredUtils;
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

    private final RestApiService apiService = new RestApiService();

    @DataProvider(name = "type-update-requests")
    public static Object[][] getTypeUpdateRequests() {
        return new Object[][]{
                {HttpMethod.PUT},
                {HttpMethod.PATCH}
        };
    }

    @Test
    public void testCreateNewComment() {
        List<Comment> existing = apiService.getAllComments();
        Integer postId = existing.get(new Random().nextInt(existing.size())).getPost_id();

        Comment expectedComment = Comment.generateComment(postId);
        Comment created = apiService.createComment(expectedComment);

        List<Comment> comments = apiService.getAllComments();
        boolean exists = comments.stream().anyMatch(c -> c.getId().equals(created.getId()));
        Assert.assertTrue(exists, "Created comment should exist in the list");
    }

    @Test(dataProvider = "type-update-requests")
    public void testUpdateComment(HttpMethod updateRequestType) {
        List<Comment> existing = apiService.getAllComments();
        Integer postId = existing.get(new Random().nextInt(existing.size())).getPost_id();

        Comment expected = Comment.generateComment(postId);
        Comment created = apiService.createComment(expected);

        String updatedBody = "updComment: " + RandomStringUtils.randomAlphabetic(8);
        created.setBody(updatedBody);
        Comment updated = apiService.updateComment(created.getId(), created, updateRequestType);

        Assert.assertEquals(updated.getId(), created.getId(), "IDs must match");
        Assert.assertEquals(updated.getBody(), updatedBody, "Body should be updated");
    }

    @Test
    public void testDeleteComment() {
        List<Comment> existing = apiService.getAllComments();
        Integer postId = existing.get(new Random().nextInt(existing.size())).getPost_id();

        Comment expected = Comment.generateComment(postId);
        Comment created = apiService.createComment(expected);

        apiService.deleteComment(created.getId());

        GetCommentByIdMethod getCommentByIdMethod = new GetCommentByIdMethod(created.getId());
        Response afterDelete = getCommentByIdMethod.execute(HttpMethod.GET);
        RestAssuredUtils.assertStatusCode(afterDelete, HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateNewUser() {
        User expected = User.generateUser();
        User created = apiService.createUser(expected);

        List<User> users = apiService.getAllUsers();
        boolean exists = UserAssertions.isUserInList(created, users);
        Assert.assertTrue(exists, "Created user should exist in the list");
    }

    @Test(dataProvider = "type-update-requests")
    public void testUpdateUser(HttpMethod updateRequestType) {
        User expected = User.generateUser();
        User created = apiService.createUser(expected);

        String updatedName = "UpdatedName" + RandomStringUtils.randomAlphabetic(3);
        created.setName(updatedName);
        User updated = apiService.updateUser(created.getId(), created, updateRequestType);

        SoftAssert softAssert = new SoftAssert();
        UserAssertions.assertUserEquals(updated, created, softAssert);
        softAssert.assertAll();
    }

    @Test
    public void testDeleteUser() {
        User expected = User.generateUser();
        User created = apiService.createUser(expected);

        apiService.deleteUser(created.getId());

        GetUserByIdMethod getUserByIdMethod = new GetUserByIdMethod(created.getId());
        Response afterDelete = getUserByIdMethod.execute(HttpMethod.GET);
        RestAssuredUtils.assertStatusCode(afterDelete, HttpStatus.NOT_FOUND);
    }
}
