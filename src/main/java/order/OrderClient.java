package order;

import base.BaseClient;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends BaseClient {

    private static final String ROOT = "/orders";
    private static final String ALL = ROOT + "/all";

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

    public ValidatableResponse getAllOrders() {
        return getSpec()
                .get(ALL)
                .then().log().all();
    }

    public ValidatableResponse getUserOrders() {
        return getSpec()
                .get(ROOT)
                .then().log().all();
    }
}