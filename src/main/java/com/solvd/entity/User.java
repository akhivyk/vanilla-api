package com.solvd.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;

    public User(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public static User generateUser() {
        return new User(RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(4) + "@mail.ex",
                "male",
                "inactive");
    }
}
