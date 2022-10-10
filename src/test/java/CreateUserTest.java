import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    User user;
    UserClient userClient;


    @After
    public void teardown() {
        userClient.delete();
    }


    @Test
    public void userCanBeCreated(){
        userClient = new UserClient(User.getUser());
        ValidatableResponse response = userClient.getResponse();
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotCreateTwoSameUsers() {
        user = User.getUser();
        userClient = new UserClient(user);
        ValidatableResponse sameUserResponse = new UserClient(user).getResponse();
        sameUserResponse.statusCode(403)
                            .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    public void canNotCreateUserWithoutEmail() {
        userClient = new UserClient(User.getWithoutEmail());
        ValidatableResponse response = userClient.getResponse();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutPassword() {
        userClient = new UserClient(User.getWithoutPassword());
        ValidatableResponse response = userClient.getResponse();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutName() {
        userClient = new UserClient(User.getWithoutName());
        ValidatableResponse response = userClient.getResponse();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutAllFields() {
        userClient = new UserClient(User.getWithoutAllFields());
        ValidatableResponse response = userClient.getResponse();
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}