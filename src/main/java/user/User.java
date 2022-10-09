package user;

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

    public static User getUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getWithoutEmail() {
        return new User(
                "",
                "P@ssw0rd",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getWithoutPassword() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "",
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getWithoutName() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@ya.ru",
                "P@ssw0rd",
                ""
        );
    }

    public static User getWithoutAllFields() {
        return new User(
                "",
                "",
                ""
        );
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getName(){
        return name;
    }
}
