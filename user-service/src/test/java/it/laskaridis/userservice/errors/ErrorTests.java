package it.laskaridis.userservice.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ErrorTests {

    @Test
    public void shouldCreateAnErrorFromAnException() {
        // Given
        ApiException anException = new ApiException("ErrorCode", "ErrorMessage");

        // When
        Error error = Error.of(anException);

        // Then
        assertThat(error.getCode(), is(equalTo(anException.getErrorCode())));
        assertThat(error.getMessage(), is(equalTo(anException.getErrorMessage())));
        assertThat(error.isRetryable(), is(equalTo(anException.isRetryable())));
        assertThat(error.getTimestamp(), is(equalTo(anException.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateAnErrorWithNoErrorCode() {
        // When
        new Error(null, "ErrorMessage");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateAnErrorWithNoErrorMessage() {
        // When
        new Error("ErrorCode", null);
    }

    @Test
    public void shouldUseCurrentTimestampByDefault() {
        // When
        Error error = new Error("ErrorCode", "ErrorMessage", Optional.of(true), Optional.empty());

        // Then
        assertThat(error.getTimestamp(), is(notNullValue()));
    }

    @Test
    public void shouldBeNonRetryableByDefault() {
        // When
        Error error = new Error("ErrorCode", "ErrorMessage", Optional.of(true), Optional.empty());

        // Then
        assertThat(error.getTimestamp(), is(notNullValue()));
    }

    @Test
    public void shouldMarshallToJson() throws JsonProcessingException {
        // Given
        Error anError = new Error("ErrorCode", "ErrorDescription");

        // When
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(anError);

        // Then
        assertThat(json, isJson());
        assertThat(json, hasJsonPath("$.code", equalTo("ErrorCode")));
        assertThat(json, hasJsonPath("$.message", equalTo("ErrorDescription")));
    }

    @Test
    public void shouldMarshallFromJson() throws IOException {
        // Given
        String aJsonError = "{" +
                "\"code\": \"ErrorCode\", " +
                "\"message\": \"ErrorMessage\"" +
                "}";

        // When
        ObjectMapper mapper = new ObjectMapper();
        Error error = mapper.readValue(aJsonError, Error.class);

        // Then
        assertThat(error.getCode(), is(equalTo("ErrorCode")));
        assertThat(error.getMessage(), is(equalTo("ErrorMessage")));
    }
}
