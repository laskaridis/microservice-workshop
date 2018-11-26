package it.laskaridis.userservice.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class ErrorListTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateErrorListWithNoErrors() {
        // When
        new ErrorList(new Error[] {});
    }

    @Test
    public void shouldMarshallToJson() throws JsonProcessingException {
        // Given
        ErrorList anErrorList = new ErrorList(new Error("ErrorCode", "ErrorMessage"));

        // When
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(anErrorList);

        // Then
        assertThat(json, isJson());
        assertThat(json, hasJsonPath("$.requestId", equalTo("RequestId")));
        assertThat(json, hasJsonPath("$.errors[0].code", equalTo("ErrorCode")));
        assertThat(json, hasJsonPath("$.errors[0].message", equalTo("ErrorMessage")));
    }

    @Test
    public void shouldWrapApiExceptions() {
        // Given
        ApiException exception1 = new ApiException("ErrorCode1", "ErrorMessage1");
        ApiException exception2 = new ApiException("ErrorCode2", "ErrorMessage2");

        // When
        ErrorList errorList = ErrorList.newErrorListWith(exception1, exception2);

        // Then
        assertThat(errorList.getErrors(), hasItem(new Error(exception1.getErrorCode(), exception1.getErrorMessage())));
        assertThat(errorList.getErrors(), hasItem(new Error(exception2.getErrorCode(), exception2.getErrorMessage())));
    }

}
