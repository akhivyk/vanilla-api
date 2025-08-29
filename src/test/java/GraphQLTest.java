import com.solvd.entity.User;
import com.solvd.enums.HttpMethodTypeEnum;
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

    @Test(priority = 1)
    public void getAllUsersTest() {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile("src/test/resources/graphql/get_all_users.graphql");

        graphQlQuery.setQuery(query);
        Response response = graphQLRequest.executeGraphQL(HttpMethodTypeEnum.POST, graphQlQuery);

        RestAssuredUtils.assertSchema(response, "graphql/assertion/graphql_rs_all_users.json");
    }

    @Test(priority = 2)
    public void createUserTest() {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile("src/test/resources/graphql/create_user.graphql");

        User generatedUser = User.generateUser();
        graphQlQuery.setQuery(query);
        graphQlQuery.setVariables(generatedUser);

        Response response = graphQLRequest.executeGraphQL(HttpMethodTypeEnum.POST, graphQlQuery);
        user = JsonPath.from(response.asString()).getObject("data.createUser.user", User.class);

        RestAssuredUtils.assertSchema(response, "graphql/assertion/graphql_rs_user_create.json");
    }

    @Test(priority = 3)
    public void getByIdUserTest() {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile("src/test/resources/graphql/get_user_by_id.graphql");

        graphQlQuery.setQuery(query);
        graphQlQuery.setVariables(user);

        Response response = graphQLRequest.executeGraphQL(HttpMethodTypeEnum.POST, graphQlQuery);
        User retrievedUser = JsonPath.from(response.asString()).getObject("data.user", User.class);

        Assert.assertEquals(retrievedUser.getId(), user.getId(), "Retrieved user id isn't equals to expected!");
    }

    @Test(priority = 4)
    public void updateUserTest() {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile("src/test/resources/graphql/update_user.graphql");

        User updatedUser = User.builder()
                .id(user.getId())
                .name("Updated Name" + RandomStringUtils.randomAlphabetic(3))
                .build();

        graphQlQuery.setQuery(query);
        graphQlQuery.setVariables(updatedUser);

        Response response = graphQLRequest.executeGraphQL(HttpMethodTypeEnum.POST, graphQlQuery);
        User retrievedUser = JsonPath.from(response.asString()).getObject("data.updateUser.user", User.class);

        Assert.assertEquals(retrievedUser.getId(), user.getId(), "Updated user id isn't equals to expected!");
        Assert.assertEquals(updatedUser.getName(), retrievedUser.getName(), "Name after updating isn't equals to expected!");
    }

    @Test(priority = 5)
    public void deleteUserTest() {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = FileReader.getQueryFromFile("src/test/resources/graphql/delete_user.graphql");

        graphQlQuery.setQuery(query);
        graphQlQuery.setVariables(user);

        Response response = graphQLRequest.executeGraphQL(HttpMethodTypeEnum.POST, graphQlQuery);
        User deletedUser = JsonPath.from(response.asString()).getObject("data.deleteUser.user", User.class);

        Assert.assertEquals(deletedUser.getId(), deletedUser.getId(), "Id of deleted user isn't equals to expected!");
    }
}
