package it.laskaridis.userservice.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class ErrorList {

    private List<Error> errors;

    private String requestId;

    public ErrorList(Error... errors) {
        this(Arrays.asList(errors));
    }

    public ErrorList(List<Error> errors) {
        Assert.notEmpty(errors, "there should be at least 1 error specified");

        this.requestId = requestId;
        this.errors = errors;
    }

    public static ErrorList newErrorListWith(ApiException... exceptions) {
        List<Error> errors = Arrays.stream(exceptions)
                .map(Error::of)
                .collect(toList());

        return new ErrorList(errors);
    }
}
