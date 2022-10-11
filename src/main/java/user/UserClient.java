package user;

import base.BaseClient;
import io.restassured.response.ValidatableResponse;

public class UserClient extends BaseClient {

    private final User user;
    private final String accessToken;
    private final String refreshToken;
    private final ValidatableResponse response;

    private final String ROOT = "/auth";
    private final String LOGIN = ROOT + "/login";
    private final String USER = ROOT + "/user";
    private final String wrongEmail = "nonexistent";
    private final String wrongPassword = "user";


    public UserClient(User user){
        this.user = user;
        this.response = create();
        this.accessToken = getAccessToken();
        this.refreshToken = getRefreshToken();
    }


    public ValidatableResponse create() {
        String REGISTRATION = ROOT + "/register";
        return getSpec()
                .body(new UserCredentials(user).getRegistrationCredentials())
                .when()
                .post(REGISTRATION)
                .then().log().all();
    }

    public String getAccessToken(){
        String token = response
                .extract()
                .path("accessToken");
//        String[] splitToken = token.split(" ");
        return token/*splitToken[1]*/;
    }

    public ValidatableResponse getResponse(){
        return response;
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

    public void logout () {
        String LOGOUT = ROOT + "/logout";
        getSpec()
                .body("{\"token\": \"{{" + refreshToken + "}}\"}")
                .when()
                .post(LOGOUT)
                .then().log().all();
    }

    public String getRefreshToken(){
        return response
                .extract()
                .path("refreshToken");
    }

    public ValidatableResponse changeEmail (){
        return getSpec()
                .body("{\"authorization\": \"{{" + accessToken + "}}\", " +
                        "\"email\": \"" + User.getUser().getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeEmailToTheSame (){
        return getSpec()
                .body("{\"authorization\": \"{{" + accessToken + "}}\", " +
                        "\"email\": \"" + user.getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changePassword (){
        return getSpec()
                .body("{\"authorization\": \"{{" + accessToken + "}}\", " +
                        "\"password\": \"newP@ssw0rd\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse changeName (){
        return getSpec()
                .body("{\"authorization\": \"{{" + accessToken + "}}\", " +
                        "\"name\": \"" + User.getUser().getName() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    public void delete() {
        if (accessToken != null) {
            getSpec()
                .body("{\"token\": \"{{" + accessToken + "}}\"}")
                .when()
                .delete(ROOT + "/user");
        }
    }
}




//    public void logout (User user) {
//        getSpec()
//                .body("{\"token\": \"" + getRefreshToken(user) + "\"}")
//                .when()
//                .post(LOGOUT)
//                .then().log().all();
//    }

//    public String getRefreshToken(User user){
//        return login(user)
//                .extract()
//                .path("refreshToken");
//    }

//    public ValidatableResponse resetPassword(User user) {
//        return getSpec()
//                .body(new UserCredentials(user).getUserEmail())
//                .when()
//                .post(PASSWORD_RESET)
//                .then().log().all();
//    }