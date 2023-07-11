package ch.bbw.onePass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
public class UserEntity implements Serializable {
    public UserEntity(String email, String secretKey, String sessionUUID) {
        this.email = email;
        this.secretKey = secretKey;
        this.sessionUUID = sessionUUID;
    }

    @Column(name = "id", updatable = false, nullable = false)
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "secret_key", nullable = false)
    private String secretKey;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "session_uuid")
    private String sessionUUID;
}
