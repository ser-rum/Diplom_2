import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersTest {

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
    public void getOrdersWithAuthorization() {
        ValidatableResponse response = new OrderClient().getUserOrders(accessToken);
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