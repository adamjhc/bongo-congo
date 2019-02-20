package com.knightlore.networking;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class ListGameObject {

    public UUID getUuid() {
        return uuid;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    UUID uuid;
    InetAddress ip;
    int port;

    public ListGameObject(UUID uuid, InetAddress ip, int port) {
        this.uuid = uuid;
        this.ip = ip;
        this.port = port;
    }
}
