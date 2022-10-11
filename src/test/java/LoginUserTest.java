import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    User user;
    UserClient userClient;


    @Before
    public void setUp(){
        user = User.getUser();
        userClient = new UserClient(user);
    }

    @After
    public void teardown() {
        userClient.delete();
    }


    @Test
    public void canLogin(){
        userClient.login()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotLoginWithWrongEmail() {
        userClient.loginWithWrongEmail()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongPassword() {
        userClient.loginWithWrongPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongEmailAndPassword() {
        userClient.loginWithWrongEmailAndPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}