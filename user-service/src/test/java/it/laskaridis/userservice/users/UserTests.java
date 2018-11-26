package it.laskaridis.userservice.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository users;

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldHaveUniqueEmail() {
        // Given
        User anExistingUser = users.save(new User("user@localhost.com", "username", "usersecret"));
        User aUserWithExistingEmail = new User(anExistingUser.getEmail(), "username2", "usersecret");

        // When
        users.save(aUserWithExistingEmail);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldHaveUniqueName() {
        // Given
        User anExistingUser = users.save(new User("user@localhost.com", "username", "usersecret"));
        User aUserWithExistingName = new User("user2@localhost.com", anExistingUser.getName(), "usersecret");

        // When
        users.save(aUserWithExistingName);
    }
}
