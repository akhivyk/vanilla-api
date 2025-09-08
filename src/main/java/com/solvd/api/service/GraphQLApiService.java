package com.solvd.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.entity.User;
import com.solvd.enums.HttpMethod;
import com.solvd.enums.HttpStatus;
import com.solvd.graphql.GraphQLRequest;
import com.solvd.graphql.GraphQlQuery;
import com.solvd.utils.ConfigReader;
import com.solvd.utils.GraphQLJsonPaths;
import com.solvd.utils.GraphQLQueries;
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
        Response response = executeGraphQL(GraphQLQueries.CREATE_USER, user);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject(GraphQLJsonPaths.CREATE_USER_NODE, User.class);
    }

    public List<User> getAllUsers() {
        Response response = executeGraphQL(GraphQLQueries.GET_ALL_USERS, null);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);

        List<Map<String, Object>> nodes = JsonPath.from(response.asString())
                .getList(GraphQLJsonPaths.ALL_USERS_NODE);

        ObjectMapper mapper = new ObjectMapper();
        return nodes.stream()
                .map(node -> mapper.convertValue(node, User.class))
                .collect(Collectors.toList());
    }

    public User getUserById(Integer id) {
        User request = User.builder().id(id).build();
        Response response = executeGraphQL(GraphQLQueries.GET_USER_BY_ID, request);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject(GraphQLJsonPaths.USER_BY_ID_NODE, User.class);
    }

    public User updateUser(User user) {
        Response response = executeGraphQL(GraphQLQueries.UPDATE_USER, user);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject(GraphQLJsonPaths.UPDATE_USER_NODE, User.class);
    }

    public User deleteUser(Integer id) {
        User request = User.builder().id(id).build();
        Response response = executeGraphQL(GraphQLQueries.DELETE_USER, request);
        RestAssuredUtils.assertStatusCode(response, HttpStatus.OK);
        return JsonPath.from(response.asString()).getObject(GraphQLJsonPaths.DELETE_USER_NODE, User.class);
    }
}
