package it.laskaridis.userservice.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUuid(String uuid);

    boolean existsByEmail(String email);

}
