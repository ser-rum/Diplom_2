import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersTest {

    UserClient userClient;


    @After
    public void teardown() {
        userClient.delete();
    }


    @Test
    public void getOrdersWithAuthorization() {
        userClient = new UserClient(User.getUser());
        ValidatableResponse response = new OrderClient().getUserOrders();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void getOrdersWithoutAuthorization() {
        ValidatableResponse response = new OrderClient().getUserOrders();
        response.statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}