package com.solvd.entity.utils;

import com.solvd.entity.User;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class UserAssertions {

    public static void assertUserEquals(User actual, User expected, SoftAssert softAssert) {
        softAssert.assertEquals(actual.getId(), expected.getId(), "IDs must match");
        softAssert.assertEquals(actual.getName(), expected.getName(), "Names must match");
        softAssert.assertEquals(actual.getEmail(), expected.getEmail(), "Emails must match");
        softAssert.assertEquals(actual.getGender(), expected.getGender(), "Genders must match");
        softAssert.assertEquals(actual.getStatus(), expected.getStatus(), "Statuses must match");
    }

    public static boolean isUserInList(User user, List<User> users) {
        return users.stream().anyMatch(u ->
                u.getId().equals(user.getId()) &&
                        u.getName().equals(user.getName()) &&
                        u.getEmail().equals(user.getEmail()) &&
                        u.getGender().equals(user.getGender()) &&
                        u.getStatus().equals(user.getStatus())
        );
    }
}

