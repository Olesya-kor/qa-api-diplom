package tests;


import io.qameta.allure.Description;
import model.LoginRequest;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;



public class LoginTest extends BaseTest {



    @Test
    @Description("Проверка успешной авторизации существующего пользователя")
    public void loginExistingUserSuccess() {


        User user =
                UserGenerator.getRandomUser();



        accessToken =
                userClient.createUser(user)
                        .statusCode(200)
                        .extract()
                        .path("accessToken");



        LoginRequest loginRequest =
                new LoginRequest(
                        user.getEmail(),
                        user.getPassword()
                );



        userClient.login(loginRequest)
                .statusCode(200)
                .body(
                        "success",
                        equalTo(true)
                )
                .body(
                        "accessToken",
                        notNullValue()
                );

    }





    @Test
    @Description("Проверка ошибки авторизации с неверным паролем")
    public void loginWithWrongPasswordShouldReturn401() {


        User user =
                UserGenerator.getRandomUser();



        accessToken =
                userClient.createUser(user)
                        .statusCode(200)
                        .extract()
                        .path("accessToken");



        LoginRequest loginRequest =
                new LoginRequest(
                        user.getEmail(),
                        "wrongPassword"
                );



        userClient.login(loginRequest)
                .statusCode(401)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo("email or password are incorrect")
                );

    }





    @After
    public void deleteUser() {


        if(accessToken != null) {

            userClient.deleteUser(accessToken);

        }

    }

}