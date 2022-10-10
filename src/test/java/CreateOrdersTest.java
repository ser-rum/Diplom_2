import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrdersTest {

    UserClient userClient;


    @After
    public void teardown() {
        userClient.delete();
    }

    @Test
    public void canCreateWithIngredientsAndAuthorization() {
        userClient = new UserClient(User.getUser());
        ValidatableResponse response = new OrderClient().createWithRightIngredient();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canCreateWithIngredientsButWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithRightIngredient();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotCreateWithoutIngredientsButWithAuthorization() {
        userClient = new UserClient(User.getUser());
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
        userClient = new UserClient(User.getUser());
        ValidatableResponse response = new OrderClient().createWithWrongIngredient();
        response.statusCode(500);
    }

    @Test
    public void canNotCreateWithWrongIngredientsAndWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().createWithWrongIngredient();
        response.statusCode(500);
    }
}