package utils;

import model.User;

import java.util.UUID;

public class UserGenerator {

    public static User getRandomUser() {

        String uuid = UUID.randomUUID().toString();

        return new User(
                "user" + uuid + "@mail.com",
                "password123",
                "name" + uuid
        );
    }
}