package it.laskaridis.userservice.errors;


import java.time.LocalDateTime;

public class ApiException extends RuntimeException {

    private final String errorCode;

    private final String errorMessage;

    private final Boolean retryable;

    private final LocalDateTime timestamp;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.retryable = false;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Boolean isRetryable() {
        return retryable;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
