package it.laskaridis.userservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
