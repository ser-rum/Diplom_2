import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersTest {
    User user;
    UserClient userClient = new UserClient();

    @After
    public void teardown() {
        userClient.delete(user);
    }

    @Test
    public void getAuthorizedUserOrders() {
        user = User.getUser();
        userClient.create(user);
        userClient.login(user);
        ValidatableResponse response = new OrderClient().getUserOrders();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void getUnauthorizedUserOrders() {
        ValidatableResponse response = new OrderClient().getUserOrders();
        response.statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}