package client;


import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import model.OrderRequest;
import utils.Constants;


import static io.restassured.RestAssured.given;



public class OrderClient {


    private static final String CREATE_ORDER =
            "/api/orders";



    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrder(OrderRequest orderRequest) {


        return given()
                .filter(new AllureRestAssured())
                .baseUri(Constants.BASE_URI)
                .header(
                        "Content-type",
                        "application/json"
                )
                .body(orderRequest)
                .when()
                .post(CREATE_ORDER)
                .then();

    }





    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderAuthorized(
            OrderRequest orderRequest,
            String accessToken) {


        return given()
                .filter(new AllureRestAssured())
                .baseUri(Constants.BASE_URI)
                .header(
                        "Content-type",
                        "application/json"
                )
                .header(
                        "Authorization",
                        accessToken
                )
                .body(orderRequest)
                .when()
                .post(CREATE_ORDER)
                .then();

    }


}