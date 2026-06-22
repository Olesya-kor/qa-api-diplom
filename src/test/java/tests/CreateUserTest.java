package tests;


import io.qameta.allure.Description;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;


public class CreateUserTest extends BaseTest {


    @Test
    @Description("Проверка успешного создания уникального пользователя")
    public void createUniqueUserSuccess() {


        User user =
                UserGenerator.getRandomUser();


        accessToken =
                userClient.createUser(user)
                        .statusCode(SC_OK)
                        .body(
                                "success",
                                equalTo(true)
                        )
                        .extract()
                        .path("accessToken");

    }


    @Test
    @Description("Проверка ошибки при создании уже существующего пользователя")
    public void createExistingUserShouldReturn403() {


        User user =
                UserGenerator.getRandomUser();


        accessToken =
                userClient.createUser(user)
                        .statusCode(SC_OK)
                        .extract()
                        .path("accessToken");


        userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo("User already exists")
                );

    }


    @Test
    @Description("Проверка ошибки создания пользователя без email")
    public void createUserWithoutEmailShouldReturn403() {


        User user =
                UserGenerator.getRandomUser();


        user.setEmail(null);


        userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo(
                                "Email, password and name are required fields"
                        )
                );

    }


    @Test
    @Description("Проверка ошибки создания пользователя без password")
    public void createUserWithoutPasswordShouldReturn403() {


        User user =
                UserGenerator.getRandomUser();


        user.setPassword(null);


        userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo(
                                "Email, password and name are required fields"
                        )
                );

    }


    @Test
    @Description("Проверка ошибки создания пользователя без name")
    public void createUserWithoutNameShouldReturn403() {


        User user =
                UserGenerator.getRandomUser();


        user.setName(null);


        userClient.createUser(user)
                .statusCode(SC_FORBIDDEN)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo(
                                "Email, password and name are required fields"
                        )
                );

    }


    @After
    public void deleteUser() {


        if(accessToken != null) {

            userClient.deleteUser(accessToken);

        }

    }

}