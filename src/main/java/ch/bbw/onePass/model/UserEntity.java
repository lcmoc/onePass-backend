package ch.bbw.onePass.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}
