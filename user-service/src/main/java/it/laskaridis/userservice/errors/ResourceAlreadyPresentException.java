package it.laskaridis.userservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceAlreadyPresentException extends ApiException {

    public ResourceAlreadyPresentException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
