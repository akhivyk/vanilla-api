import com.solvd.entity.User;
import com.solvd.enums.HttpMethod;
import com.solvd.graphql.GraphQLRequest;
import com.solvd.graphql.GraphQlQuery;
import com.solvd.utils.FileReader;
import com.solvd.utils.RestAssuredUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GraphQLTest {

    private User user;

    private Response executeGraphQL(String queryPath, Object variables) {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile(queryPath);
        graphQlQuery.setQuery(query);

        if (variables != null) {
            graphQlQuery.setVariables(variables);
        }

        return graphQLRequest.executeGraphQL(HttpMethod.POST, graphQlQuery);
    }

    @Test(priority = 1)
    public void getAllUsersTest() {
        Response response = executeGraphQL(
                "graphql/get_all_users.graphql",
                null
        );

        RestAssuredUtils.assertSchema(response, "graphql/assertion/graphql_rs_all_users.json");
    }

    @Test(priority = 2)
    public void createUserTest() {
        User generatedUser = User.generateUser();

        Response response = executeGraphQL(
                "graphql/create_user.graphql",
                generatedUser
        );

        user = JsonPath.from(response.asString()).getObject("data.createUser.user", User.class);

        RestAssuredUtils.assertSchema(response, "graphql/assertion/graphql_rs_user_create.json");
    }

    @Test(priority = 3)
    public void getByIdUserTest() {
        Response response = executeGraphQL(
                "graphql/get_user_by_id.graphql",
                user
        );

        User retrievedUser = JsonPath.from(response.asString()).getObject("data.user", User.class);
        Assert.assertEquals(retrievedUser.getId(), user.getId(), "Retrieved user id isn't equals to expected!");
    }

    @Test(priority = 4)
    public void updateUserTest() {
        User updatedUser = User.builder()
                .id(user.getId())
                .name("Updated Name" + RandomStringUtils.randomAlphabetic(3))
                .build();

        Response response = executeGraphQL(
                "graphql/update_user.graphql",
                updatedUser
        );

        User retrievedUser = JsonPath.from(response.asString()).getObject("data.updateUser.user", User.class);

        Assert.assertEquals(retrievedUser.getId(), user.getId(), "Updated user id isn't equals to expected!");
        Assert.assertEquals(updatedUser.getName(), retrievedUser.getName(), "Name after updating isn't equals to expected!");
    }

    @Test(priority = 5)
    public void deleteUserTest() {
        Response response = executeGraphQL(
                "graphql/delete_user.graphql",
                user
        );

        User deletedUser = JsonPath.from(response.asString()).getObject("data.deleteUser.user", User.class);
        Assert.assertEquals(deletedUser.getId(), user.getId(), "Id of deleted user isn't equals to expected!");
    }
}
