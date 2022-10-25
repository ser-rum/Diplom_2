import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Зарегистрированный пользователь может изменить электронную почту")
    public void loginUserCanChangeEmail(){
        userClient.changeEmail(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Зарегистрированый пользователь может изменить пароль")
    public void loginUserCanChangePassword() {
        userClient.changePassword(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь может изменить имя")
    public void loginUserCanChangeName() {
        userClient.changeName(response)
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Незарегистрированный пользователь не может изменить электронную почту")
    public void unLoginUserCanNotChangeEmail() {
        userClient.changeEmailWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Незарегистрированный пользователь не может изменить пароль")
    public void unLoginUserCanNotChangePassword() {
        userClient.changePasswordWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Незарегистрированный пользователь не может изменить имя")
    public void unLoginUserCanNotChangeName() {
        userClient.changeNameWithoutAuthorization()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}