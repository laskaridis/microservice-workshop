package it.laskaridis.userservice.users;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserNotFoundExceptionTests {

    @Test
    public void shouldCreateAnInstanceByUserName() {
        // Given
        UserNotFoundException anException = new UserNotFoundException("testuser");

        // Then
        assertThat(anException.getErrorCode(), is(equalTo(UserNotFoundException.ERROR_CODE)));
        assertThat(anException.getErrorMessage(), containsString("testuser"));
    }
}
