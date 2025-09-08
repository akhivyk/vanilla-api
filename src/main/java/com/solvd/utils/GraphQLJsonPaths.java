package com.solvd.utils;

public final class GraphQLJsonPaths {

    private GraphQLJsonPaths() {}

    public static final String CREATE_USER_NODE = "data.createUser.user";
    public static final String ALL_USERS_NODE = "data.users.edges.node";
    public static final String USER_BY_ID_NODE = "data.user";
    public static final String UPDATE_USER_NODE = "data.updateUser.user";
    public static final String DELETE_USER_NODE = "data.deleteUser.user";
}
