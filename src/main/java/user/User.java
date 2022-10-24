package user;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Получение валидного пользователя")
    public static User getUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    @Step("Получение пользователя без электронной почты")
    public static User getWithoutEmail() {
        return new User(
                "",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    @Step("Получение пользователя без пароля")
    public static User getWithoutPassword() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    @Step("Получение пользователя без имени")
    public static User getWithoutName() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "P@ssw0rd",
                ""
        );
    }

    @Step("Получение пользователя без всех полей")
    public static User getWithoutAllFields() {
        return new User(
                "",
                "",
                ""
        );
    }

    @Step("Получение электронной почты пользователя")
    public String getEmail() {
        return email;
    }
    @Step("Получение пароля пользователя")
    public String getPassword() {
        return password;
    }
    @Step("Получение имени пользователя")
    public String getName(){
        return name;
    }
}
