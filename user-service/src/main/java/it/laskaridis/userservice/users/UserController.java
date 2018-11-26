package it.laskaridis.userservice.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository users;

    private final UserRegistrationService userRegistrationService;

    public UserController(UserRepository users, UserRegistrationService userRegistrationService) {
        this.users = users;
        this.userRegistrationService = userRegistrationService;
    }

    @PreAuthorize("#oauth2.hasScope('user.me')")
    @RequestMapping("/{uuid}")
    public User show(@PathVariable String uuid) {
        return users.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
    }

    @RequestMapping(method = POST)
    public ResponseEntity create(@RequestBody User user) {
        user = userRegistrationService.register(user);
        URI location = getUri(user);
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("#oauth2.hasScope('user.delete')")
    @RequestMapping(method = DELETE)
    public ResponseEntity destroy(@PathVariable String uuid) {
        User user = users.findById(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        users.delete(user);
        return ResponseEntity.ok().build();
    }

    private URI getUri(User resource) {
        return fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(resource.getUuid())
                    .toUri();
    }
}
