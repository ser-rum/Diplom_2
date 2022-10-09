import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    User user;
    UserClient userClient;

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @After
    public void teardown() {
        userClient.delete(user);
    }


    @Test
    public void userCanBeCreated(){
        user = User.getUser();
        ValidatableResponse response = userClient.create(user);
        response.statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void canNotCreateTwoSameUsers() {
        user = User.getUser();
        userClient.create(user);
        ValidatableResponse sameUserResponse = userClient.create(user);
        sameUserResponse.statusCode(403)
                            .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    public void canNotCreateUserWithoutEmail() {
        ValidatableResponse response = userClient.create(User.getWithoutEmail());
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutPassword() {
        ValidatableResponse response = userClient.create(User.getWithoutPassword());
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutName() {
        ValidatableResponse response = userClient.create(User.getWithoutName());
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void canNotCreateUserWithoutAllFields() {
        ValidatableResponse response = userClient.create(User.getWithoutAllFields());
        response.statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}