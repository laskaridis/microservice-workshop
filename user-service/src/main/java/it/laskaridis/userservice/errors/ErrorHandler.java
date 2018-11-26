package it.laskaridis.userservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return new ResponseEntity(ErrorList.newErrorListWith(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyPresentException.class)
    public ResponseEntity handleResourceAlreadyPresentexception(ResourceAlreadyPresentException e, WebRequest request) {
        return new ResponseEntity(ErrorList.newErrorListWith(e), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleInternalServerError(Throwable e, WebRequest request) {
        ErrorList list = new ErrorList(Error.newNonRetryableError("UnexpectedError", "An unexpected error occurred"));
        return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
