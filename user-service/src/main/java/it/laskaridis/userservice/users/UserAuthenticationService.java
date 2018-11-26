package it.laskaridis.userservice.users;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository users;

    @Autowired
    public UserAuthenticationService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        User user = users.findByUuid(uuid).orElseThrow(() -> new UsernameNotFoundException(uuid));
        Collection<GrantedAuthority> authorities = Sets.newHashSet(
                new SimpleGrantedAuthority("user.me")
        );
        return new org.springframework.security.core.userdetails.User(user.getUuid(), user.getSecret(), authorities);
    }
}
