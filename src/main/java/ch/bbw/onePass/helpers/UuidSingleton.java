package ch.bbw.onePass.helpers;

import java.util.UUID;

public class UuidSingleton {
    private static UuidSingleton instance = null;
    private UUID uuid;

    private UuidSingleton() {
        uuid = UUID.randomUUID();
    }

    public static UuidSingleton getInstance() {
        if (instance == null) {
            instance = new UuidSingleton();
        }
        return instance;
    }

    public UUID getUuid() {
        return uuid;
    }
}
