package ch.bbw.onePass.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "USER")
public class UserEntity implements UserDetails {
    public UserEntity(String email, String secretKey) {
        this.email = email;
        this.secretKey = secretKey;
    }

    @Column(name = "id", updatable = false, nullable = false)
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "secret_key", nullable = false)
    private String secretKey;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
