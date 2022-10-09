package user;

public class UserCredentials {

    private final String email;
    private final String password;
    private final String name;


    public UserCredentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
    }

    public UserCredentials(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.name = user.getName();
    }

    public UserCredentials(String email, User user) {
        this.email = email;
        this.password = user.getPassword();
        this.name = user.getName();
    }

    public UserCredentials(User user, String password) {
        this.email = user.getEmail();
        this.password = password;
        this.name = user.getName();
    }

    public String getRegistrationCredentials(){
        return "{\"email\": \"" + email + "\", \"password\": \""
                + password + "\", \"name\": \"" + name + "\"}";
    }

    public String getLoginCredentials(){
        return "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
    }

    public String getUserEmail(){
        return "{\"email\": \"" + email + "\"}";
    }
}