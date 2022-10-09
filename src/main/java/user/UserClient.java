package user;

import base.BaseClient;
import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseClient {

    private final String ROOT = "/auth";
    private final String PASSWORD_RESET = "/password-reset";
    private final String REGISTRATION = ROOT + "/register";
    private final String LOGIN = ROOT + "/login";
    private final String LOGOUT = ROOT + "/logout";
    private final String wrongEmail = "nonexistent";
    private final String wrongPassword = "user";


    public ValidatableResponse create(User user) {
        return getSpec()
                .body(new UserCredentials(user).getRegistrationCredentials())
                .when()
                .post(REGISTRATION)
                .then().log().all();
    }

    public ValidatableResponse resetPassword(User user) {
        return getSpec()
                .body(new UserCredentials(user).getUserEmail())
                .when()
                .post(PASSWORD_RESET)
                .then().log().all();
    }

    public ValidatableResponse login (User user) {
        return getSpec()
                .body(new UserCredentials(user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongEmail (User user) {
        return getSpec()
                .body(new UserCredentials(wrongEmail, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongPassword (User user) {
        return getSpec()
                .body(new UserCredentials(user, wrongPassword).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongEmailAndPassword (User user) {
        return getSpec()
                .body(new UserCredentials(wrongEmail, wrongPassword, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public String getRefreshToken(User user){
        return login(user)
                .extract()
                .path("refreshToken");
    }

    public void logout (User user) {
        getSpec()
            .body("{\"token\": \"" + getRefreshToken(user) + "\"}")
            .when()
            .post(LOGOUT)
            .then().log().all();
    }

    public void delete(User user) {
        if (user != null) {
            getSpec()
                    .when()
                    .delete(ROOT + "/user");
        }
    }
}