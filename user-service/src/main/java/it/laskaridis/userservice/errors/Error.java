package it.laskaridis.userservice.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@NoArgsConstructor // required for jackson
public class Error {

    private String code;

    private String message;

    private boolean retryable;

    private String timestamp;

    public static Error of(ApiException e) {
        return new Error(
                e.getErrorCode(),
                e.getErrorMessage(),
                Optional.of(e.isRetryable()),
                Optional.of(e.getTimestamp())
        );
    }

    public static Error newRetryableError(String code, String message) {
        return new Error(code, message, Optional.of(true), Optional.empty());
    }

    public static Error newNonRetryableError(String code, String message) {
        return new Error(code, message);
    }

    public Error(String code, String message) {
        this(code, message, Optional.empty(), Optional.empty());
    }

    public Error(String code, String message, Optional<Boolean> retryable, Optional<LocalDateTime> timestamp) {
        Assert.notNull(code, "error code cannot be empty");
        Assert.notNull(message, "error message cannot be empty");

        this.code = code;
        this.message = message;
        this.retryable = retryable.orElse(false);
        this.timestamp = timestamp
                .orElse(LocalDateTime.now())
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Error error = (Error) o;

        if (!code.equals(error.code)) return false;
        return message.equals(error.message);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}
