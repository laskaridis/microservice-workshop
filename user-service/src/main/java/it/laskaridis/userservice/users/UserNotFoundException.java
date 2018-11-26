package it.laskaridis.userservice.users;

import it.laskaridis.userservice.errors.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public static final String ERROR_CODE = "NoSuchUser";
    public static final String ERROR_MESSAGE = "The user with name `%s` does not exist";

    public UserNotFoundException(String name) {
        super(ERROR_CODE, String.format(ERROR_MESSAGE, name));
    }
}
