package user;

import base.BaseClient;
import io.qameta.allure.Step;
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


    @Step("Создание пользователя")
    public ValidatableResponse create() {
        String REGISTRATION = ROOT + "/register";
        return getSpec()
                .body(new UserCredentials(user).getRegistrationCredentials())
                .when()
                .post(REGISTRATION)
                .then().log().all();
    }

    @Step("Получение токена доступа")
    public String getAccessToken(ValidatableResponse response){
        String token = response
                .extract()
                .path("accessToken");
        String[] splitToken = token.split(" ");
        return splitToken[1];
    }

    @Step("Авторизация")
    public ValidatableResponse login () {
        return getSpec()
                .body(new UserCredentials(user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Авторизация с неправильной электронной почтой")
    public ValidatableResponse loginWithWrongEmail () {
        return getSpec()
                .body(new UserCredentials(wrongEmail, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Авторизация с неправильным паролем")
    public ValidatableResponse loginWithWrongPassword () {
        return getSpec()
                .body(new UserCredentials(user, wrongPassword).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Авторизация с неправильными электронной почтой и паролем")
    public ValidatableResponse loginWithWrongEmailAndPassword () {
        return getSpec()
                .body(new UserCredentials(wrongEmail, wrongPassword, user).getLoginCredentials())
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Изменение электронной почты")
    public ValidatableResponse changeEmail (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"email\": \"" + User.getUser().getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение электронной почты без авторизации")
    public ValidatableResponse changeEmailWithoutAuthorization(){
        return getSpec()
                .body("{\"email\": \"" + User.getUser().getEmail() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение пароля")
    public ValidatableResponse changePassword (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"password\": \"newP@ssw0rd\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение пароля без авторизации")
    public ValidatableResponse changePasswordWithoutAuthorization (){
        return getSpec()
                .body("{\"password\": \"newP@ssw0rd\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение имени")
    public ValidatableResponse changeName (ValidatableResponse response){
        return getSpec()
                .auth().oauth2(getAccessToken(response))
                .body("{\"name\": \"" + User.getUser().getName() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Изменение имени без авторизации")
    public ValidatableResponse changeNameWithoutAuthorization (){
        return getSpec()
                .body("{\"name\": \"" + User.getUser().getName() + "\"}")
                .when()
                .patch(USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public void delete(ValidatableResponse response) {
        getSpec()
                .auth().oauth2(getAccessToken(response))
                .delete(ROOT + "/user");
    }
}