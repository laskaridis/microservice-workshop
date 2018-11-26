package it.laskaridis.userservice.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.laskaridis.userservice.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "secret", nullable = false)
    @JsonIgnore
    private String secret;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<SecurityToken> securityTokens = new HashSet<>();

    public User(String email, String username, String secret) {
        this(null,
                UUID.newUUID(),
                email,
                username,
                secret,
                new HashSet<>()
        );
    }

    public SecurityToken securityToken() {
        return this.securityTokens.stream()
                .filter(SecurityToken::getValid)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no valid security token present for for user `" + this.uuid + "`"));
    }

}
