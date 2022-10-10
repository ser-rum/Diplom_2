package order;

import base.BaseClient;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends BaseClient {

    private static final String ROOT = "/orders";

    public ValidatableResponse createWithRightIngredient() {
        return getSpec()
                .body(OrderIngredients.getRightIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse createWithWrongIngredient() {
        return getSpec()
                .body(OrderIngredients.getWrongIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse createWithoutIngredients() {
        return getSpec()
                .body(OrderIngredients.getNoIngredients())
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse getUserOrders() {
        return getSpec()
                .get(ROOT)
                .then().log().all();
    }
}