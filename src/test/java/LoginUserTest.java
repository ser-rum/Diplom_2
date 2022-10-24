import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    User user;
    UserClient userClient;
    ValidatableResponse response;

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
    @DisplayName("Можно залогиниться")
    public void canLogin(){
        userClient.login()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя залогиниться с неправильной электронной почтой")
    public void canNotLoginWithWrongEmail() {
        userClient.loginWithWrongEmail()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Нельзя залогиниться с неправильным паролем")
    public void canNotLoginWithWrongPassword() {
        userClient.loginWithWrongPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Нельзя залогиниться с неправильными электронной почтой и паролем")
    public void canNotLoginWithWrongEmailAndPassword() {
        userClient.loginWithWrongEmailAndPassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}