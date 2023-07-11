package ch.bbw.onePass.JsonReturnModels;

import ch.bbw.onePass.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UserUuidDto {

    public UserUuidDto(Long userId, String userSecretKey, String userEmail, String uuid) {
        this.userId = userId;
        this.userSecretKey = userSecretKey;
        this.userEmail = userEmail;
        this.uuid = uuid;
    }

    @JsonProperty("id")
    private Long userId;
    @JsonProperty("secretKey")
    private String userSecretKey;
    @JsonProperty("email")

    private String userEmail;

    @JsonProperty("uuid")
    private String uuid;

}
