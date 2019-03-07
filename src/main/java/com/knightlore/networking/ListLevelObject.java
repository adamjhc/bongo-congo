package com.knightlore.networking;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class ListLevelObject {

    UUID uuid;
    String name;
    String created_by;

    public ListLevelObject(UUID uuid, String name, String created_by) {
        this.uuid = uuid;
        this.name = name;
        this.created_by = created_by;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getCreated_by() {
        return created_by;
    }

}
