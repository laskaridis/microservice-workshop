package it.laskaridis.userservice.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "security_tokens")
public class SecurityToken {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @JsonIgnore
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public static SecurityToken newTokenExpiringAt(LocalDateTime expireDate) {
        return new SecurityToken(
                null,
                UUID.randomUUID().toString(),
                expireDate,
                LocalDateTime.now(),
                Boolean.TRUE,
                null
        );
    }
}
