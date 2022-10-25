package order;

import io.qameta.allure.Step;

public class OrderIngredients {
    private static final String rightIngredients = "[\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\"]";
    private static final String wrongIngredients = "[\"wrong\", \"ingredients\"]";
    private static final String noIngredients = "[]";

    @Step("Получение правильных ингредиентов")
    public static String getRightIngredients(){
        return "{\"ingredients\": " + rightIngredients + "}";
    }

    @Step("Получение неправильных ингредиентов")
    public static String getWrongIngredients(){
        return "{\"ingredients\": " + wrongIngredients + "}";
    }

    @Step("Неполучение ингредиентов")
    public static String getNoIngredients(){
        return "{\"ingredients\": " + noIngredients + "}";
    }
}
