package com.solvd.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.entity.User;
import com.solvd.enums.HttpMethod;
import com.solvd.enums.HttpStatus;
import com.solvd.graphql.GraphQLRequest;
import com.solvd.graphql.GraphQlQuery;
import com.solvd.utils.ConfigReader;
import com.solvd.utils.RestAssuredUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphQLApiService {

    private final String token;

    public GraphQLApiService(String token) {
        this.token = token;
    }

    private Response executeGraphQL(String queryPath, Object variables) {
        GraphQLRequest graphQLRequest = new GraphQLRequest();
        GraphQlQuery graphQlQuery = new GraphQlQuery();

        String query = ConfigReader.getQueryFromFile(queryPath);
        graphQlQuery.setQuery(query);

        if (variables != null) {
            graphQlQuery.setVariables(variables);
        }

        return graphQLRequest.executeGraphQL(HttpMethod.POST, graphQlQuery);
    }

    public User createUser(User user) {
        Response response = executeGraphQL("graphql/create_user.graphql", user);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject("data.createUser.user", User.class);
    }

    public List<User> getAllUsers() {
        Response response = executeGraphQL("graphql/get_all_users.graphql", null);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);

        List<Map<String, Object>> nodes = JsonPath.from(response.asString())
                .getList("data.users.edges.node");

        ObjectMapper mapper = new ObjectMapper();
        return nodes.stream()
                .map(node -> mapper.convertValue(node, User.class))
                .collect(Collectors.toList());
    }

    public User getUserById(Integer id) {
        User request = User.builder().id(id).build();
        Response response = executeGraphQL("graphql/get_user_by_id.graphql", request);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject("data.user", User.class);
    }

    public User updateUser(User user) {
        Response response = executeGraphQL("graphql/update_user.graphql", user);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject("data.updateUser.user", User.class);
    }

    public User deleteUser(Integer id) {
        User request = User.builder().id(id).build();
        Response response = executeGraphQL("graphql/delete_user.graphql", request);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject("data.deleteUser.user", User.class);
    }
}
