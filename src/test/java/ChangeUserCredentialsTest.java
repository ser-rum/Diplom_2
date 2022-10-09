import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ChangeUserCredentialsTest {

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
    public void loginUserCanChangeEmail(){
        userClient.login(user)
                .statusCode(200)
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void loginUserCanChangeName() {
        userClient.loginWithWrongEmailAndPassword(user)
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginUserCanNotChangeEmailToExist() {
        userClient.loginWithWrongEmail(user)
                .statusCode(403)
                .assertThat().body("message", equalTo("User with such email already exists"));
    }

    @Test
    public void unLoginUserCanNotChangeEmail() {
        userClient.loginWithWrongPassword(user)
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void unLoginUserCanNotChangeName() {
        userClient.login(User.getWithoutEmail())
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}