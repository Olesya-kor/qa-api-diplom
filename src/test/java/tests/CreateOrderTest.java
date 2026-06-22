package tests;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import model.OrderRequest;
import model.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import utils.Constants;
import utils.UserGenerator;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;


public class CreateOrderTest extends BaseTest {


    private OrderRequest validOrder() {

        return new OrderRequest(
                Arrays.asList(
                        Constants.INGREDIENT_HASH
                )
        );
    }


    private OrderRequest emptyOrder() {

        return new OrderRequest(
                Collections.emptyList()
        );
    }


    private OrderRequest wrongHashOrder() {

        return new OrderRequest(
                Collections.singletonList(
                        "60d3b41abdac0026a733c7"
                )
        );
    }


    @Step("Создать пользователя и получить токен")
    private void createUser() {

        User user = UserGenerator.getRandomUser();

        accessToken =
                userClient.createUser(user)
                        .statusCode(
                                HttpStatus.SC_OK
                        )
                        .extract()
                        .path("accessToken");
    }


    @Test
    @Description("Проверка создания заказа авторизованным пользователем")
    public void createOrderWithAuthorizationSuccess() {

        createUser();

        orderClient.createOrderAuthorized(
                        validOrder(),
                        accessToken
                )
                .statusCode(
                        HttpStatus.SC_OK
                )
                .body(
                        "success",
                        equalTo(true)
                );
    }


    @Test
    @Description("Проверка создания заказа без авторизации")
    public void createOrderWithoutAuthorizationSuccess() {

        orderClient.createOrder(validOrder())
                .statusCode(
                        HttpStatus.SC_OK
                )
                .body(
                        "success",
                        equalTo(true)
                );
    }


    @Test
    @Description("Проверка создания заказа с ингредиентами")
    public void createOrderWithIngredientsSuccess() {

        orderClient.createOrder(validOrder())
                .statusCode(
                        HttpStatus.SC_OK
                )
                .body(
                        "success",
                        equalTo(true)
                );
    }


    @Test
    @Description("Проверка ошибки создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsShouldReturn400() {

        orderClient.createOrder(emptyOrder())
                .statusCode(
                        HttpStatus.SC_BAD_REQUEST
                )
                .body(
                        "success",
                        equalTo(false)
                )
                .body(
                        "message",
                        equalTo(
                                "Ingredient ids must be provided"
                        )
                );
    }


    @Test
    @Description("Проверка ошибки создания заказа с неверным хешем ингредиента")
    public void createOrderWithWrongHashShouldReturn500() {

        orderClient.createOrder(wrongHashOrder())
                .statusCode(
                        HttpStatus.SC_INTERNAL_SERVER_ERROR
                );
    }


    @After
    public void deleteUser() {

        if (accessToken != null) {

            userClient.deleteUser(accessToken);

        }
    }
}