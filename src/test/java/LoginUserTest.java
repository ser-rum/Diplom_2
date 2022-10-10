import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    User user;
    UserClient userClient;


    @After
    public void teardown() {
        userClient.delete();
    }


    @Test
    public void canLogin(){
        user = User.getUser();
        userClient = new UserClient(user);
        userClient.login()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotLoginWithWrongEmail() {
        user = User.getUser();
        userClient = new UserClient(user);
        userClient.loginWithWrongEmail()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongPassword() {
        user = User.getUser();
        userClient = new UserClient(user);
        userClient.loginWithWrongPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithWrongEmailAndPassword() {
        user = User.getUser();
        userClient = new UserClient(user);
        userClient.loginWithWrongEmailAndPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutEmail() {
        user = User.getWithoutEmail();
        userClient = new UserClient(user);
        userClient.login()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutPassword() {
        user = User.getWithoutPassword();
        userClient = new UserClient(user);
        userClient.login()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void canNotLoginWithoutAllFields() {
        user = User.getWithoutAllFields();
        userClient = new UserClient(user);
        userClient.login()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}