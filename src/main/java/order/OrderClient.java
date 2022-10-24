package order;

import base.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends BaseClient {

    private static final String ROOT = "/orders";

    @Step("Создание заказа с правильными ингредиентами")
    public ValidatableResponse createWithRightIngredient(String accessToken) {
        return getSpec()
                .auth().oauth2(accessToken)
                .body(OrderIngredients.getRightIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа с правильными ингредиентами")
    public ValidatableResponse createWithRightIngredient() {
        return getSpec()
                .body(OrderIngredients.getRightIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа с неправильными ингредиентами")
    public ValidatableResponse createWithWrongIngredient(String accessToken) {
        return getSpec()
                .auth().oauth2(accessToken)
                .body(OrderIngredients.getWrongIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа с неправильными ингредиентами")
    public ValidatableResponse createWithWrongIngredient() {
        return getSpec()
                .body(OrderIngredients.getWrongIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createWithoutIngredients(String accessToken) {
        return getSpec()
                .auth().oauth2(accessToken)
                .body(OrderIngredients.getNoIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createWithoutIngredients() {
        return getSpec()
                .body(OrderIngredients.getNoIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Получение списка заказов пользователя")
    public ValidatableResponse getUserOrders(String accessToken) {
        return getSpec()
                .auth().oauth2(accessToken)
                .get(ROOT)
                .then().log().all();
    }

    @Step("Получение списка заказов пользователя")
    public ValidatableResponse getUserOrders() {
        return getSpec()
                .get(ROOT)
                .then().log().all();
    }
}