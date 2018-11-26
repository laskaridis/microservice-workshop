package it.laskaridis.userservice.fixtures;

import it.laskaridis.userservice.users.User;

import java.util.concurrent.atomic.AtomicLong;

public final class Users {

    private static final AtomicLong ID = new AtomicLong(1);

    public static User build() {
        Long id = ID.getAndIncrement();
        return new User("user" + id + "@localhost.com", "user", "usersecret");
    }
}
