package com.solvd.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class JsonUtils {

    private JsonUtils() {}

    public static <T> T toObject(Response response, Class<T> clazz) {
        return JsonPath.from(response.asString()).getObject("", clazz);
    }

    public static <T> List<T> toList(Response response, Class<T> clazz) {
        return JsonPath.from(response.asString()).getList("", clazz);
    }
}
