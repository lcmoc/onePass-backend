package ch.bbw.onePass.helpers;

import java.util.UUID;

public class UUIDUtils {

    public static boolean compareUUIDs(String uuid1, String uuid2) {
        try {
            UUID frontendUUID = UUID.fromString(uuid1);
            UUID sessionUUID = UUID.fromString(uuid2);
            return frontendUUID.equals(sessionUUID);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
