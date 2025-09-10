package com.solvd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private Integer id;
    private Integer post_id;
    private String name;
    private String email;
    private String body;

    public Comment(Integer post_id, String name, String email, String body) {
        this.post_id = post_id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public static Comment generateComment(Integer postId) {
        return new Comment(postId,
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(4) + "@mail.ex",
                RandomStringUtils.randomAlphabetic(25));
    }
}
