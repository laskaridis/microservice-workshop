package it.laskaridis.userservice.users;

import it.laskaridis.userservice.errors.ApiException;

public class UserEmailTakenException extends ApiException {

    public static final String ERROR_CODE = "UserEmailTaken";
    public static final String ERROR_MESSAGE = "The user email `%s` is already taken";

    public UserEmailTakenException(String email) {
        super(ERROR_CODE, String.format(ERROR_MESSAGE, email));
    }
}
