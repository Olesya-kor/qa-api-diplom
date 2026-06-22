package tests;


import io.qameta.allure.Description;
import model.LoginRequest;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.UserGenerator;


import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;



public class LoginTest extends BaseTest {


    private User user;



    @Before
    public void createUser() {


        user =
                UserGenerator.getRandomUser();


        accessToken =
                userClient.createUser(user)
                        .statusCode(SC_OK)
                        .extract()
                        .path("accessToken");

    }




    @Test
    @Description("Проверка успешной авторизации существующего пользователя")
    public void loginExistingUserSuccess() {


        LoginRequest loginRequest =
                new LoginRequest(
                        user.getEmail(),
                        user.getPassword()
                );


        userClient.login(loginRequest)
                .statusCode(SC_OK)
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


        LoginRequest loginRequest =
                new LoginRequest(
                        user.getEmail(),
                        "wrongPassword"
                );


        userClient.login(loginRequest)
                .statusCode(SC_UNAUTHORIZED)
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo("email or password are incorrect")
                );

    }





    @Test
    @Description("Проверка ошибки авторизации с неверным логином")
    public void loginWithWrongEmailShouldReturn401() {


        LoginRequest loginRequest =
                new LoginRequest(
                        "wrong@email.com",
                        user.getPassword()
                );


        userClient.login(loginRequest)
                .statusCode(SC_UNAUTHORIZED)
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