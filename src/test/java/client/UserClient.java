package client;


import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import model.LoginRequest;
import model.User;
import utils.Constants;


import static io.restassured.RestAssured.given;



public class UserClient {


    private static final String CREATE_USER =
            "/api/auth/register";


    private static final String LOGIN_USER =
            "/api/auth/login";


    private static final String DELETE_USER =
            "/api/auth/user";





    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {


        return given()
                .filter(new AllureRestAssured())
                .baseUri(Constants.BASE_URI)
                .header(
                        "Content-type",
                        "application/json"
                )
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();

    }






    @Step("Авторизация пользователя")
    public ValidatableResponse login(LoginRequest loginRequest) {


        return given()
                .filter(new AllureRestAssured())
                .baseUri(Constants.BASE_URI)
                .header(
                        "Content-type",
                        "application/json"
                )
                .body(loginRequest)
                .when()
                .post(LOGIN_USER)
                .then();

    }







    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {


        return given()
                .filter(new AllureRestAssured())
                .baseUri(Constants.BASE_URI)
                .header(
                        "Authorization",
                        accessToken
                )
                .when()
                .delete(DELETE_USER)
                .then();

    }


}