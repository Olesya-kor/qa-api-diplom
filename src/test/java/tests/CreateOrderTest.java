package tests;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import model.OrderRequest;
import model.User;
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
                Arrays.asList(Constants.INGREDIENT_HASH)
        );
    }


    private OrderRequest emptyOrder() {

        return new OrderRequest(
                Collections.emptyList()
        );
    }


    private OrderRequest invalidOrder() {

        return new OrderRequest(
                Arrays.asList("invalid_hash")
        );
    }



    @Step("Создать пользователя и получить токен")
    private void createUser() {

        User user = UserGenerator.getRandomUser();

        accessToken =
                userClient.createUser(user)
                        .statusCode(200)
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
                .statusCode(200)
                .body("success", equalTo(true));
    }





    @Test
    @Description("Проверка создания заказа без авторизации")
    public void createOrderWithoutAuthorizationSuccess() {


        orderClient.createOrder(validOrder())
                .statusCode(200)
                .body("success", equalTo(true));

    }





    @Test
    @Description("Проверка создания заказа с корректным ингредиентом")
    public void createOrderWithIngredientsSuccess() {


        orderClient.createOrder(validOrder())
                .statusCode(200)
                .body("success", equalTo(true));

    }





    @Test
    @Description("Проверка ошибки создания заказа без ингредиентов")
    public void createOrderWithoutIngredientsShouldReturn400() {


        orderClient.createOrder(emptyOrder())
                .statusCode(400);

    }





    @Test
    @Description("Проверка ошибки создания заказа с неверным ингредиентом")
    public void createOrderWithInvalidIngredientHashShouldReturn400() {


        orderClient.createOrder(invalidOrder())
                .statusCode(400);

    }





    @After
    public void deleteUser() {


        if(accessToken != null) {

            userClient.deleteUser(accessToken);

        }

    }

}