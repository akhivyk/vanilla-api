package com.solvd.graphql;

import lombok.Data;

@Data
public class GraphQlQuery {

    private String query;

    private Object variables;

}
