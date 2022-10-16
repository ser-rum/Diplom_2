package user;

import base.BaseClient;
import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseClient {

    private final User user;

    private final String ROOT = "/auth";
    private final String LOGIN = ROOT + "/login";
    private final String USER = ROOT + "/user";
    private final String wrongEmail = "nonexistent";
    private final String wrongPassword = "user";


    public UserClient(User user){
        this.user = user;
    }


    public ValidatableResponse create() {
        String REGISTRATION = ROOT + "/register";
        return getSpec()
                .body(new UserCredentials(user).getRegistrationCredentials())
                .when()
                .post(REGISTRATION)
                .then().log().all();
    }

    public String getAccessToken(ValidatableResponse response){
        String token = response
                .extract()
                .path("accessToken");
        String[] splitToken = token.split(" ");
        return splitToken[1];
    }

    public ValidatableResponse login () {
        return getSpec()
                .body(new UserCredentials(user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongEmail () {
        return getSpec()
                .body(new UserCredentials(wrongEmail, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongPassword () {
        return getSpec()
                .body(new UserCredentials(user, wrongPassword).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse loginWithWrongEmailAndPassword () {
        return getSpec()
                .body(new UserCredentials(wrongEmail, wrongPassword, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse changeEmail (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"email\": \"" + User.getUser().getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeEmailWithoutAuthorization(){
        return getSpec()
                .body("{\"email\": \"" + User.getUser().getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changePassword (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"password\": \"newP@ssw0rd\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changePasswordWithoutAuthorization (){
        return getSpec()
                .body("{\"password\": \"newP@ssw0rd\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeName (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"name\": \"" + User.getUser().getName() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeNameWithoutAuthorization (){
        return getSpec()
                .body("{\"name\": \"" + User.getUser().getName() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public void delete(ValidatableResponse response) {
        getSpec()
                .auth().oauth2(getAccessToken(response))
                .delete(ROOT + "/user");
    }
}