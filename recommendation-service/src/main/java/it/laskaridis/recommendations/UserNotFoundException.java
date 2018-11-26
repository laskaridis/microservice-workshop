package it.laskaridis.recommendations;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("user " + username + " not exists");
    }
}
