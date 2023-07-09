package ch.bbw.onePass.helpers;

import ch.bbw.onePass.model.UserEntity;
import java.util.UUID;

public class UserUuidDto {
    private UserEntity user;
    private UUID uuid;

    public UserUuidDto(UserEntity user, UUID uuid) {
        this.user = user;
        this.uuid = uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public UUID getUuid() {
        return uuid;
    }
}
