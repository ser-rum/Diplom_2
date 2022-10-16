import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    UserClient userClient;
    ValidatableResponse response;


    @Test
    public void userCanBeCreated(){
        userClient = new UserClient(User.getUser());
        response = userClient.create();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
        userClient.delete(response);
    }

    @Test
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
    public void     canNotCreateUserWithoutEmail() {
        userClient = new UserClient(User.getWithoutEmail());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutPassword() {
        userClient = new UserClient(User.getWithoutPassword());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutName() {
        userClient = new UserClient(User.getWithoutName());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutAllFields() {
        userClient = new UserClient(User.getWithoutAllFields());
        response = userClient.create();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}