import org.junit.After;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserCredentialsTest {

    UserClient userClient;


    @After
    public void teardown() {
        userClient.delete();
    }


    @Test
    public void loginUserCanChangeEmail(){
        userClient = new UserClient(User.getUser());
        userClient.create();
        userClient.changeEmail()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginUserCanChangePassword() {
        userClient = new UserClient(User.getUser());
        userClient.create();
        userClient.changePassword()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginUserCanChangeName() {
        userClient = new UserClient(User.getUser());
        userClient.create();
        userClient.changeName()
                .statusCode(200)
                .assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginUserCanNotChangeEmailToExist() {
        userClient = new UserClient(User.getUser());
        userClient.create();
        userClient.changeEmailToTheSame()
                .statusCode(403)
                .assertThat().body("message", equalTo("User with such email already exists"));
    }

    @Test
    public void unLoginUserCanNotChangeEmail() {
        userClient.changeEmail()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void unLoginUserCanNotChangePassword() {
        userClient.changePassword()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    public void unLoginUserCanNotChangeName() {
        userClient.changeName()
                .statusCode(401)
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}