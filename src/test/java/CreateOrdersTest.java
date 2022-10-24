import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrdersTest {

    User user;
    UserClient userClient;
    ValidatableResponse response;
    String accessToken;

    @Before
    public void setUp(){
        user = User.getUser();
        userClient = new UserClient(user);
        response = userClient.create();
        accessToken = userClient.getAccessToken(response);
    }

    @After
    public void teardown() {
        userClient.delete(response);
    }

    @Test
    @DisplayName("Зарегистрированный пользователь может создать заказ с ингредиентами")
    public void canCreateWithIngredientsAndAuthorization() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.createWithRightIngredient(accessToken);
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Незарегистрированный пользователь может создать заказ с ингредиентами")
    public void canCreateWithIngredientsButWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithRightIngredient();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь не может создать заказ без ингредиентов")
    public void canNotCreateWithoutIngredientsButWithAuthorization() {
        ValidatableResponse response = new OrderClient().createWithoutIngredients(accessToken);
        response.statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Незарегистрированный пользователь не может создать заказ без ингредиентов")
    public void canNotCreateWithoutIngredientsAndWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithoutIngredients();
        response.statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь не может создать заказ с неправильными ингредиентами")
    public void canNotCreateWithWrongIngredientsButWithAuthorization() {
        ValidatableResponse response = new OrderClient().createWithWrongIngredient(accessToken);
        response.statusCode(500);
    }

    @Test
    @DisplayName("Незарегистрированный пользователь не может создать заказ с неправильными ингредиентами")
    public void canNotCreateWithWrongIngredientsAndWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithWrongIngredient();
        response.statusCode(500);
    }
}