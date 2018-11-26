package it.laskaridis.userservice.users;

import it.laskaridis.userservice.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository users;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegistrationServiceImpl(UserRepository users) {
        this.users = users;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) {
        if (users.existsByEmail(user.getEmail())) {
            throw new UserEmailTakenException(user.getEmail());
        }
        user.setSecret(passwordEncoder.encode(user.getSecret()));
        user.setUuid(UUID.newUUID());
        user.getSecurityTokens().add(createSecurityToken(user));
        return users.save(user);
    }

    private SecurityToken createSecurityToken(User user) {
        LocalDateTime tokenExpirationDate = LocalDateTime.now().plusYears(1);
        SecurityToken token = SecurityToken.newTokenExpiringAt(tokenExpirationDate);
        token.setUuid(UUID.newUUID());
        token.setOwner(user);
        return token;
    }
}
