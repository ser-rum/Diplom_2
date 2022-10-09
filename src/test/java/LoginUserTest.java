import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    User user;
    UserClient userClient = new UserClient();

    @Before
    public void setup() {
        user = User.getUser();
        userClient.create(user);
    }

    @After
    public void teardown() {
        userClient.delete(user);
    }


    @Test
    public void canLogin(){
        userClient.login(user)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotLoginWithWrongEmail() {
        userClient.loginWithWrongEmail(user)
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongPassword() {
        userClient.loginWithWrongPassword(user)
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongEmailAndPassword() {
        userClient.loginWithWrongEmailAndPassword(user)
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutEmail() {
        userClient.login(User.getWithoutEmail())
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutPassword() {
        userClient.login(User.getWithoutPassword())
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutAllFields() {
        userClient.login(User.getWithoutAllFields())
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}