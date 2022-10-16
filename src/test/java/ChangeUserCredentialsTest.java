import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserCredentialsTest {

    User user;
    UserClient userClient;
    private ValidatableResponse response;


    @Before
    public void setUp(){
        user = User.getUser();
        userClient = new UserClient(user);
        response = userClient.create();
    }

    @After
    public void teardown() {
        userClient.delete(response);
    }


    @Test
    public void loginUserCanChangeEmail(){
        userClient.changeEmail(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginUserCanChangePassword() {
        userClient.changePassword(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginUserCanChangeName() {
        userClient.changeName(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void unLoginUserCanNotChangeEmail() {
        userClient.changeEmailWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void unLoginUserCanNotChangePassword() {
        userClient.changePasswordWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void unLoginUserCanNotChangeName() {
        userClient.changeNameWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}