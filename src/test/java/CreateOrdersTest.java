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
    UserClient userClient = new UserClient();

//    @Before
//    public void setup() {
//        user = User.getUser();
//        userClient.create(user);
//    }

    @After
    public void teardown() {
        userClient.delete(user);
    }

    @Test
    public void canCreateWithIngredientsAndAuthorization() {
        ValidatableResponse response = new OrderClient().createWithRightIngredient();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canCreateWithIngredientsButWithoutAuthorization() {
//        userClient.logout(user);
        ValidatableResponse response = new OrderClient().createWithRightIngredient();
        response.statusCode(401);
    }

    @Test
    public void canNotCreateWithoutIngredientsButWithAuthorization() {
        userClient.login(user);
        ValidatableResponse response = new OrderClient().createWithoutIngredients();
        response.statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void canNotCreateWithoutIngredientsAndWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithoutIngredients();
        response.statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    public void canNotCreateWithWrongIngredientsButWithAuthorization() {
        userClient.login(user);
        ValidatableResponse response = new OrderClient().createWithWrongIngredient();
        response.statusCode(500);
    }

    @Test
    public void canNotCreateWithWrongIngredientsAndWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithWrongIngredient();
        response.statusCode(500);
    }
}