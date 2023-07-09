package ch.bbw.onePass.helpers;

import ch.bbw.onePass.model.UserEntity;
import java.util.UUID;

public class UserUuidDto {
    private UserEntity user;
    private String uuid;

    public UserUuidDto(UserEntity user, String uuid) {
        this.user = user;
        this.uuid = uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getUuid() {
        return uuid;
    }
}
