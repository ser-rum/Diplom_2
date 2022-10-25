import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    UserClient userClient;
    ValidatableResponse response;


    @Test
    @DisplayName("Можно создать пользователя")
    public void userCanBeCreated(){
        userClient = new UserClient(User.getUser());
        response = userClient.create();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
        userClient.delete(response);
    }

    @Test
    @DisplayName("Нельзя создать два одинаковых пользователя")
    public void canNotCreateTwoSameUsers() {
        User user = User.getUser();
        userClient = new UserClient(user);
        response = userClient.create();
        ValidatableResponse newResponse = new UserClient(user).create();
        newResponse.statusCode(403)
                            .assertThat().body("message", equalTo("User already exists"));
        userClient.delete(response);
    }

    @Test
    @DisplayName("Нельзя создать пользователя без электронной почты")
    public void canNotCreateUserWithoutEmail() {
        userClient = new UserClient(User.getWithoutEmail());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Нельзя создать пользователя без пароля")
    public void canNotCreateUserWithoutPassword() {
        userClient = new UserClient(User.getWithoutPassword());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Нельзя создать пользователя без имени")
    public void canNotCreateUserWithoutName() {
        userClient = new UserClient(User.getWithoutName());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Нельзя создать пользователя без всех полей")
    public void canNotCreateUserWithoutAllFields() {
        userClient = new UserClient(User.getWithoutAllFields());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}