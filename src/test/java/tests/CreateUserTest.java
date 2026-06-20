package tests;


import io.qameta.allure.Description;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;


import static org.hamcrest.Matchers.equalTo;



public class CreateUserTest extends BaseTest {



    @Test
    @Description("Проверка успешного создания уникального пользователя")
    public void createUniqueUserSuccess() {


        User user =
                UserGenerator.getRandomUser();


        accessToken =
                userClient.createUser(user)
                        .statusCode(200)
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
                        .statusCode(200)
                        .extract()
                        .path("accessToken");



        userClient.createUser(user)
                .statusCode(403)
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
    @Description("Проверка ошибки создания пользователя без обязательного поля")
    public void createUserWithoutRequiredFieldShouldReturn403() {


        User user =
                new User(
                        null,
                        "password123",
                        "name"
                );



        userClient.createUser(user)
                .statusCode(403)
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