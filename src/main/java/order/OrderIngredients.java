package order;

public class OrderIngredients {
    private static final String rightIngredients = "[\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\"]";
    private static final String wrongIngredients = "[\"wrong\", \"ingredients\"]";
    private static final String noIngredients = "[]";

    public static String getRightIngredients(){
        return "{\"ingredients\": " + rightIngredients + "}";
    }

    public static String getWrongIngredients(){
        return "{\"ingredients\": " + wrongIngredients + "}";
    }

    public static String getNoIngredients(){
        return "{\"ingredients\": " + noIngredients + "}";
    }
}
