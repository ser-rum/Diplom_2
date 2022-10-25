package base;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    public static String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    @Step("Формирование базы запроса")
    protected static RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL);
    }
}
